package json;

import com.google.gson.JsonObject;

import java.util.Map;

public class ClientRequest {

    private String command;
    private JsonObject parameters;

    public ClientRequest(String command, JsonObject parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public String getCommand() {
        return command;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParameters(JsonObject parameters) {
        this.parameters = parameters;
    }
}
