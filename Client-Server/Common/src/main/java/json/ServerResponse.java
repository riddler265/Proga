package json;

import java.util.List;

public class ServerResponse {

    private String type;
    private List<String> parameters;

    public String getType() {
        return type;
    }

    public String[]  getParameters() {
        return parameters.toArray(String[]::new);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
