package json;

import com.google.gson.JsonObject;

public record ClientRequest(String command, JsonObject parameters) {}
