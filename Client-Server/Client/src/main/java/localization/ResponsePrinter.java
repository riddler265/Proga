package localization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Prints server responses received as raw JSON strings.
 * Does not deserialize into Response class — works directly with JsonObject.
 */
public class ResponsePrinter {

    private static final DateTimeFormatter INPUT_FORMAT  = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void print(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        boolean success = obj.get("success").getAsBoolean();
        String message = obj.has("message") && !obj.get("message").isJsonNull()
                ? obj.get("message").getAsString() : "";

        if (obj.has("products") && !obj.get("products").isJsonNull()) {
            JsonArray products = obj.getAsJsonArray("products");
            if (products.size() == 0) {
                System.out.println("Collection is empty.");
            } else {
                System.out.println("Total elements: " + products.size());
                products.forEach(p -> {
                    System.out.println();
                    System.out.println(formatProduct(p.getAsJsonObject()));
                });
            }
        } else {
            System.out.println("[" + (success ? "OK" : "ERROR") + "] " + message);
        }
    }

    private static String formatProduct(JsonObject p) {
        StringBuilder sb = new StringBuilder();

        sb.append("ID:               ").append(p.get("id").getAsInt()).append("\n");
        sb.append("Name:             ").append(p.get("name").getAsString()).append("\n");
        sb.append("Creation date:    ").append(formatDate(getString(p, "creationDate"))).append("\n");
        sb.append("Price:            ").append(isNull(p, "price") ? "—" : p.get("price").getAsFloat()).append("\n");
        sb.append("Part number:      ").append(isNull(p, "partNumber") ? "—" : p.get("partNumber").getAsString()).append("\n");
        sb.append("Manufacture cost: ").append(p.get("manufactureCost").getAsFloat()).append("\n");
        sb.append("Unit of measure:  ").append(getString(p, "unitOfMeasure")).append("\n");

        if (p.has("coordinates") && !p.get("coordinates").isJsonNull()) {
            JsonObject coords = p.getAsJsonObject("coordinates");
            sb.append("Coordinates:      X=").append(coords.get("x").getAsInt())
                    .append(", Y=").append(coords.get("y").getAsInt()).append("\n");
        } else {
            sb.append("Coordinates:      —\n");
        }

        if (p.has("owner") && !p.get("owner").isJsonNull()) {
            JsonObject owner = p.getAsJsonObject("owner");
            sb.append("Owner:\n");
            sb.append("  Name:           ").append(getString(owner, "name")).append("\n");
            sb.append("  Birthday:       ").append(isNull(owner, "birthday") ? "—" : formatDate(getString(owner, "birthday"))).append("\n");
            sb.append("  Height:         ").append(owner.get("height").getAsFloat()).append("\n");
            sb.append("  Passport ID:    ").append(isNull(owner, "passportID") ? "—" : getString(owner, "passportID")).append("\n");
            sb.append("  Hair color:     ").append(isNull(owner, "hairColor") ? "—" : getString(owner, "hairColor")).append("\n");
        } else {
            sb.append("Owner:            —\n");
        }

        return sb.toString().stripTrailing();
    }

    private static String formatDate(String raw) {
        if (raw == null || raw.equals("—")) return "—";
        try {
            return LocalDateTime.parse(raw, INPUT_FORMAT).format(OUTPUT_FORMAT);
        } catch (Exception e) {
            return raw;
        }
    }

    private static boolean isNull(JsonObject obj, String field) {
        return !obj.has(field) || obj.get(field).isJsonNull();
    }

    private static String getString(JsonObject obj, String field) {
        return isNull(obj, field) ? "—" : obj.get(field).getAsString();
    }
}