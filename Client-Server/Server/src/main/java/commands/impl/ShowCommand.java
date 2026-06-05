package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ShowCommand implements Command {

    private static final Logger logger = Logger.getLogger(ShowCommand.class.getName());
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        logger.info("Executing SHOW — fetching all elements sorted");

        List<Product> sorted = collectionManager.getCollection().stream()
                .sorted()
                .collect(Collectors.toList());

        if (sorted.isEmpty()) {
            logger.info("SHOW result: collection is empty");
            return new Response(true, "Collection is clear", new ArrayList<>());
        }

        logger.info("SHOW result: returning " + sorted.size() + " elements");
        return new Response(true, "show", sorted);
    }
}