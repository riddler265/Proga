package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;

import java.util.logging.Logger;

public class RemoveByIdCommand implements Command {

    private static final Logger logger = Logger.getLogger(RemoveByIdCommand.class.getName());
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        if (parameter == null) {
            logger.warning("REMOVE_BY_ID failed: no id parameter provided");
            return new Response(false, "Element ID is not specified.");
        }

        int id = parameter.getAsInt();
        logger.info("Executing REMOVE_BY_ID — looking for product id=" + id);

        Product product = collectionManager.getProductById(id);

        if (product == null) {
            logger.warning("REMOVE_BY_ID failed: product id=" + id + " not found");
            return new Response(false, "Element with id=" + id + " not found.");
        }

        collectionManager.remove(product);
        logger.info("REMOVE_BY_ID success: removed product id=" + id);
        return new Response(true, "Element id=" + id + " has been deleted.");
    }
}