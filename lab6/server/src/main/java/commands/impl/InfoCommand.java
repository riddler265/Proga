package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;

import java.util.logging.Logger;

public class InfoCommand implements Command {

    private static final Logger logger = Logger.getLogger(InfoCommand.class.getName());
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        logger.info("Executing INFO — returning collection metadata");

        String info = "Collection type: " + collectionManager.getCollection().getClass().getSimpleName() + "\n" +
                "Initialization date: " + collectionManager.getInitializationDate() + "\n" +
                "Number of elements: " + collectionManager.getCollection().size();

        logger.info("INFO result: " + collectionManager.getCollection().size() + " elements");
        return new Response(true, info);
    }
}