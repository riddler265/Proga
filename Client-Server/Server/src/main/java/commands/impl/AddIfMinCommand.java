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
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        logger.info("Executing ADD_IF_MIN — parsing product and comparing with collection minimum");

        Product product = ProductParser.buildProduct(jProduct, jCoordinates, jPerson);

        if (collectionManager.getCollection().isEmpty()) {
            product.assignServerFields();
            collectionManager.add(product);
            logger.info("ADD_IF_MIN success: collection was empty, product added id=" + product.getId());
            return new Response(true, "Коллекция была пуста — элемент добавлен. ID: " + product.getId());
        }

        Product lowest = collectionManager.getLowestProduct();
        logger.info("ADD_IF_MIN comparing with lowest: '" + lowest.getName() + "'");

        if (product.compareTo(lowest) < 0) {
            product.assignServerFields();
            collectionManager.add(product);
            logger.info("ADD_IF_MIN success: product is minimum, added id=" + product.getId());
            return new Response(true, "Элемент является минимальным и добавлен. ID: " + product.getId());
        }

        logger.info("ADD_IF_MIN: product is not minimum, not added");
        return new Response(false, "Элемент не является минимальным и не был добавлен.");
    }
}
