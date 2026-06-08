package commands.impl;

import commands.Command;
import commands.ProductParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;
import java.util.logging.Logger;

public class AddIfMinCommand implements Command {
    private static final Logger logger = Logger.getLogger(AddIfMinCommand.class.getName());
    private final CollectionManager cm;
    public AddIfMinCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        Product product = ProductParser.buildProduct(jProduct, jCoords, jPerson);
        if (cm.getCollection().isEmpty()) {
            boolean ok = cm.add(product, login);
            if (!ok) return new Response(false, "response.error.db");
            return new Response(true, "response.add_if_min.empty", new String[]{ String.valueOf(product.getId()) });
        }
        Product lowest = cm.getLowestProduct();
        if (product.compareTo(lowest) < 0) {
            boolean ok = cm.add(product, login);
            if (!ok) return new Response(false, "response.error.db");
            return new Response(true, "response.add_if_min.success", new String[]{ String.valueOf(product.getId()) });
        }
        return new Response(false, "response.add_if_min.not_min");
    }
}
