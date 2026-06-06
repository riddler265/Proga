package util.json;

import com.google.gson.*;
import communication.Command;
import communication.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonManager {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                            new JsonPrimitive(src.format(FORMATTER)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            LocalDateTime.parse(json.getAsString(), FORMATTER))
            .serializeNulls()
            .create();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static String serializeRequest(Request request) {
        return GSON.toJson(request);
    }

    public static Command getCommand(String request) {
        return Command.valueOf(JsonParser.parseString(request).getAsJsonObject()
                .get("command").getAsString().toUpperCase());
    }

    public static JsonObject getProduct(String request) {
        JsonElement el = JsonParser.parseString(request).getAsJsonObject().get("product");
        if (el == null || el.isJsonNull()) return null;
        return el.getAsJsonObject();
    }

    public static JsonElement getParameter(String request) {
        JsonElement el = JsonParser.parseString(request).getAsJsonObject().get("parameter");
        if (el == null || el.isJsonNull()) return null;
        // если это объект {"parameter": value} — достаём value
        if (el.isJsonObject()) {
            return el.getAsJsonObject().get("parameter");
        }
        return el;
    }

    public static JsonObject getNestedObject(JsonObject parent, String field) {
        if (parent == null) return null;
        JsonElement el = parent.get(field);
        if (el == null || el.isJsonNull()) return null;
        return el.getAsJsonObject();
    }
}