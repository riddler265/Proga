package json;

import localization.AnnounceManager;

import java.util.List;

public class ServerResponse {

    private String type;
    private List<String> parameters;

    //region getters
    public String getType() {
        return type;
    }

    public List<String>  getParameters() {
        return parameters;
    }//endregion

    //region setters
    public void setType(String type) {
        this.type = type;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }//endregion

    @Override
    public String toString() {
        return AnnounceManager.getInstance().format(type, parameters.toArray(String[]::new));
    }
}
