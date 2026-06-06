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
    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        if (parameter == null) {
            logger.warning("UPDATE_ID failed: no id parameter provided");
            return new Response(false, "Element ID is not specified.");
        }

        int id = parameter.getAsInt();
        logger.info("Executing UPDATE_ID — looking for product id=" + id);

        Product product = collectionManager.getProductById(id);

        if (product == null) {
            logger.warning("UPDATE_ID failed: product id=" + id + " not found");
            return new Response(false, "Element with id=" + id + " not found.");
        }

        ProductParser.updateProduct(product, jProduct, jCoordinates, jPerson);

        logger.info("UPDATE_ID success: updated product id=" + id + " name='" + product.getName() + "'");
        return new Response(true, "Element id=" + id + " successfully updated.");
    }
}