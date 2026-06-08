package localization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponsePrinter {

    private static final DateTimeFormatter INPUT_FORMAT  = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static final String SEP = "─".repeat(50);

    public static void print(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        // Push-уведомление от сервера
        if (obj.has("notification") && !obj.get("notification").isJsonNull()) {
            AnnounceManager am = AnnounceManager.getInstance();
            String msg = obj.get("notification").getAsString();
            System.out.println();
            System.out.println(SEP);
            try { System.out.println("  [" + am.format("notification.label") + "] " + msg); }
            catch (Exception e) { System.out.println("  [NOTIFICATION] " + msg); }
            System.out.println(SEP);
            return;
        }

        boolean success = obj.get("success").getAsBoolean();
        String messageText = resolveMessage(obj, success);

        if (obj.has("products") && !obj.get("products").isJsonNull()) {
            JsonArray products = obj.getAsJsonArray("products");
            System.out.println();
            if (products.size() == 0) {
                System.out.println("  " + messageText);
            } else {
                System.out.println("  " + messageText);
                System.out.println();
                products.forEach(p -> {
                    System.out.println(SEP);
                    System.out.println(formatProduct(p.getAsJsonObject()));
                });
                System.out.println(SEP);
            }
        } else {
            System.out.println();
            String prefix = success ? "  [OK] " : "  [ERROR] ";
            System.out.println(prefix + messageText);
        }
        System.out.println();
    }

    private static String resolveMessage(JsonObject obj, boolean success) {
        AnnounceManager am = AnnounceManager.getInstance();

        if (obj.has("messageKey") && !obj.get("messageKey").isJsonNull()) {
            String key = obj.get("messageKey").getAsString();
            String[] args = new String[0];
            if (obj.has("messageArgs") && !obj.get("messageArgs").isJsonNull()) {
                JsonArray arr = obj.getAsJsonArray("messageArgs");
                args = new String[arr.size()];
                for (int i = 0; i < arr.size(); i++) args[i] = arr.get(i).getAsString();
            }
            try { return am.format(key, args); }
            catch (Exception e) { return "[missing key: " + key + "]"; }
        }

        if (obj.has("message") && !obj.get("message").isJsonNull()) {
            return obj.get("message").getAsString();
        }
        return "";
    }

    private static String formatProduct(JsonObject p) {
        AnnounceManager am = AnnounceManager.getInstance();
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(field(am, "product.field.id",               String.valueOf(p.get("id").getAsInt())));
        sb.append(field(am, "product.field.name",             p.get("name").getAsString()));
        sb.append(field(am, "product.field.creation_date",    formatDate(getString(p, "creationDate"))));
        sb.append(field(am, "product.field.price",            isNull(p, "price")       ? dash(am) : String.valueOf(p.get("price").getAsFloat())));
        sb.append(field(am, "product.field.part_number",      isNull(p, "partNumber")  ? dash(am) : p.get("partNumber").getAsString()));
        sb.append(field(am, "product.field.manufacture_cost", String.valueOf(p.get("manufactureCost").getAsFloat())));
        sb.append(field(am, "product.field.unit_of_measure",  getString(p, "unitOfMeasure")));

        if (!isNull(p, "coordinates")) {
            JsonObject c = p.getAsJsonObject("coordinates");
            sb.append(field(am, "product.field.coordinates",
                    "X=" + c.get("x").getAsInt() + ", Y=" + c.get("y").getAsInt()));
        } else {
            sb.append(field(am, "product.field.coordinates", dash(am)));
        }

        if (!isNull(p, "owner")) {
            JsonObject o = p.getAsJsonObject("owner");
            sb.append(fieldLabel(am, "product.field.owner")).append("\n");
            sb.append(field(am, "person.field.name",       getString(o, "name")));
            sb.append(field(am, "person.field.birthday",   isNull(o, "birthday") ? dash(am) : formatDate(getString(o, "birthday"))));
            sb.append(field(am, "person.field.height",     String.valueOf(o.get("height").getAsFloat())));
            sb.append(field(am, "person.field.passport_id",isNull(o, "passportID") ? dash(am) : getString(o, "passportID")));
            sb.append(field(am, "person.field.hair_color", isNull(o, "hairColor")  ? dash(am) : getString(o, "hairColor")));
        } else {
            sb.append(field(am, "product.field.owner", dash(am)));
        }

        return sb.toString().stripTrailing();
    }

    /** Строка вида "  Название          : значение\n" */
    private static String field(AnnounceManager am, String labelKey, String value) {
        return "  " + String.format("%-22s", fieldLabel(am, labelKey)) + ": " + value + "\n";
    }

    private static String fieldLabel(AnnounceManager am, String key) {
        try { return am.format(key); }
        catch (Exception e) { return key; }
    }

    private static String dash(AnnounceManager am) {
        try { return am.format("value.none"); } catch (Exception e) { return "—"; }
    }

    private static String formatDate(String raw) {
        if (raw == null || raw.equals("—")) return "—";
        try { return LocalDateTime.parse(raw, INPUT_FORMAT).format(OUTPUT_FORMAT); }
        catch (Exception e) { return raw; }
    }

    private static boolean isNull(JsonObject obj, String field) {
        return !obj.has(field) || obj.get(field).isJsonNull();
    }

    private static String getString(JsonObject obj, String field) {
        return isNull(obj, field) ? "—" : obj.get(field).getAsString();
    }
}
