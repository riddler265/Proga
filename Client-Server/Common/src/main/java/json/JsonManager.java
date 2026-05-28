package json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonManager {

    private static final Gson GSON = new Gson();

    public static ServerResponse responseDeserialization(String jsonString) throws JsonSyntaxException {
        return GSON.fromJson(jsonString, ServerResponse.class);
    }

    public static String responseSerialization(ServerResponse serverResponse) {
        return GSON.toJson(serverResponse + "\n");
    }

    public static ClientRequest requestDeserialization(String jsonString) throws JsonSyntaxException {
        return GSON.fromJson(jsonString, ClientRequest.class);
    }

    public static String requestSerialization(ClientRequest clientRequest) {
        return GSON.toJson(clientRequest) + "\\n";
    }

}
