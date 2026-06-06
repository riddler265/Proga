package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;

import java.util.logging.Logger;

public class RemoveAllByPriceCommand implements Command {

    private static final Logger logger = Logger.getLogger(RemoveAllByPriceCommand.class.getName());
    private final CollectionManager collectionManager;

    public RemoveAllByPriceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        if (parameter == null) {
            logger.warning("REMOVE_ALL_BY_PRICE failed: no price parameter provided");
            return new Response(false, "Price is not specified.");
        }

        float price = parameter.getAsFloat();
        logger.info("Executing REMOVE_ALL_BY_PRICE — removing elements with price=" + price);

        long countBefore = collectionManager.getCollection().size();
        collectionManager.getCollection().removeIf(p -> p.getPrice() != null && p.getPrice() == price);
        long removed = countBefore - collectionManager.getCollection().size();

        logger.info("REMOVE_ALL_BY_PRICE success: removed " + removed + " elements with price=" + price);
        return new Response(true, "Removed elements with price=" + price + ": " + removed);
    }
}