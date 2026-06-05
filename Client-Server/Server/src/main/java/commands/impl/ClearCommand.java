package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;

import java.util.logging.Logger;

public class ClearCommand implements Command {

    private static final Logger logger = Logger.getLogger(ClearCommand.class.getName());
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        int sizeBefore = collectionManager.getCollection().size();
        logger.info("Executing CLEAR — removing " + sizeBefore + " elements");

        collectionManager.clear();

        logger.info("CLEAR success: collection is now empty");
        return new Response(true, "Collection cleared. Removed elements: " + sizeBefore);
    }
}