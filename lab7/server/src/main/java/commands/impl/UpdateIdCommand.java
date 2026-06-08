package commands.impl;

import commands.Command;
import commands.ProductParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;
import java.util.logging.Logger;

public class UpdateIdCommand implements Command {
    private static final Logger logger = Logger.getLogger(UpdateIdCommand.class.getName());
    private final CollectionManager cm;
    public UpdateIdCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_id");
        int id = param.getAsInt();
        Product product = cm.getProductById(id);
        if (product == null) return new Response(false, "response.error.not_found", new String[]{ String.valueOf(id) });
        if (!login.equals(product.getOwnerLogin())) return new Response(false, "response.error.not_owner");
        ProductParser.updateProduct(product, jProduct, jCoords, jPerson);
        product.setOwnerLogin(login); // сохраняем владельца после обновления
        boolean ok = cm.update(product, login);
        if (!ok) return new Response(false, "response.error.not_owner");
        return new Response(true, "response.update_id.success", new String[]{ String.valueOf(id) });
    }
}
