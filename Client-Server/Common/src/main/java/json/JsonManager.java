package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import enums.Commands;

public class JsonManager {

    public static Gson GSON;

    public JsonManager() {
        GSON = new Gson();
    }

    public static Commands getCommand(String request) {
        return Commands.valueOf(JsonParser.parseString(request).getAsJsonObject().get("command").getAsString().toUpperCase());
    }

    public static JsonObject getProduct(String request) {
        return JsonParser.parseString(request).getAsJsonObject().getAsJsonObject("product");
    }

    public static JsonObject getParameter(String request) {
        return JsonParser.parseString(request).getAsJsonObject().getAsJsonObject("parameter");
    }

}
