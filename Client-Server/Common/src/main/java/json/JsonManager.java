package json;

import com.google.gson.*;
import enums.Commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonManager {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) ->
                            new JsonPrimitive(src.format(FORMATTER)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            LocalDateTime.parse(json.getAsString(), FORMATTER))
            .serializeNulls()
            .create();

    public static Commands getCommand(String request) {
        return Commands.valueOf(JsonParser.parseString(request).getAsJsonObject()
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
        return el;
    }

    public static JsonObject getNestedObject(JsonObject parent, String field) {
        if (parent == null) return null;
        JsonElement el = parent.get(field);
        if (el == null || el.isJsonNull()) return null;
        return el.getAsJsonObject();
    }
}