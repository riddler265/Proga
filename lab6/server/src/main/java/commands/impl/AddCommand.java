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
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        logger.info("Executing ADD — parsing product from request");

        Product product = ProductParser.buildProduct(jProduct, jCoordinates, jPerson);
        product.assignServerFields();

        collectionManager.add(product);

        logger.info("ADD success: added product id=" + product.getId() + " name='" + product.getName() + "'");
        return new Response(true, "Element successfully added. ID: " + product.getId());
    }
}