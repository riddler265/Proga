package localization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResponsePrinter {

    public static void print(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        boolean success = obj.get("success").getAsBoolean();
        String message = obj.has("message") && !obj.get("message").isJsonNull()
                ? obj.get("message").getAsString() : "";

        // Если есть список продуктов — выводим каждый
        if (obj.has("products") && !obj.get("products").isJsonNull()) {
            JsonArray products = obj.getAsJsonArray("products");
            if (products.size() == 0) {
                System.out.println("Коллекция пуста.");
            } else {
                products.forEach(p -> System.out.println(formatProduct(p.getAsJsonObject())));
            }
        } else {
            // Иначе просто выводим message
            System.out.println(message);
        }
    }

    private static String formatProduct(JsonObject p) {
        return "ID: " + p.get("id").getAsInt() +
                " | " + p.get("name").getAsString() +
                " | price: " + (p.get("price").isJsonNull() ? "—" : p.get("price").getAsFloat()) +
                " | cost: " + p.get("manufactureCost").getAsFloat() +
                " | unit: " + p.get("unitOfMeasure").getAsString();
    }
}