package json;

public class ClientRequest {

    private String command;
    private Object parameters;

    public ClientRequest(String command, Object parameters) {
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

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }
}
