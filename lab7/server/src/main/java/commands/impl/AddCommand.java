package commands.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import commands.Command;
import commands.ProductParser;
import managers.CollectionManager;
import model.Product;
import network.Response;
import java.util.logging.Logger;

public class AddCommand implements Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());
    private final CollectionManager cm;
    public AddCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        Product product = ProductParser.buildProduct(jProduct, jCoords, jPerson);
        boolean ok = cm.add(product, login);
        if (!ok) return new Response(false, "response.error.db");
        logger.info("ADD success id=" + product.getId());
        return new Response(true, "response.add.success", new String[]{ String.valueOf(product.getId()) });
    }
}
