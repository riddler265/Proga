package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;

import java.util.logging.Logger;

public class RemoveGreaterCommand implements Command {

    private static final Logger logger = Logger.getLogger(RemoveGreaterCommand.class.getName());
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        if (parameter == null) {
            logger.warning("REMOVE_GREATER failed: no id parameter provided");
            return new Response(false, "Element ID is not specified.");
        }

        int id = parameter.getAsInt();
        logger.info("Executing REMOVE_GREATER — using product id=" + id + " as threshold");

        Product target = collectionManager.getProductById(id);

        if (target == null) {
            logger.warning("REMOVE_GREATER failed: product id=" + id + " not found");
            return new Response(false, "Element with id=" + id + " not found.");
        }

        long countBefore = collectionManager.getCollection().size();
        collectionManager.getCollection().removeIf(p -> p.compareTo(target) > 0);
        long removed = countBefore - collectionManager.getCollection().size();

        logger.info("REMOVE_GREATER success: removed " + removed + " elements greater than '" + target.getName() + "'");
        return new Response(true, "Removed elements: " + removed);
    }
}