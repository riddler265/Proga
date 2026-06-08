package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;
import java.util.logging.Logger;

public class RemoveByIdCommand implements Command {
    private static final Logger logger = Logger.getLogger(RemoveByIdCommand.class.getName());
    private final CollectionManager cm;
    public RemoveByIdCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_id");
        int id = param.getAsInt();
        var product = cm.getProductById(id);
        if (product == null) return new Response(false, "response.error.not_found", new String[]{ String.valueOf(id) });
        if (!login.equals(product.getOwnerLogin())) return new Response(false, "response.error.not_owner");
        boolean ok = cm.removeById(id, login);
        if (!ok) return new Response(false, "response.error.not_found", new String[]{ String.valueOf(id) });
        return new Response(true, "response.remove_by_id.success", new String[]{ String.valueOf(id) });
    }
}
