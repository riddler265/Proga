package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import model.Product;
import network.Response;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FilterGreaterThanManufactureCostCommand implements Command {

    private static final Logger logger = Logger.getLogger(FilterGreaterThanManufactureCostCommand.class.getName());
    private final CollectionManager collectionManager;

    public FilterGreaterThanManufactureCostCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoordinates,
                            JsonObject jPerson, JsonElement parameter) {
        if (parameter == null) {
            logger.warning("FILTER_GREATER_THAN_MANUFACTURE_COST failed: no manufactureCost parameter provided");
            return new Response(false, "Manufacture cost is not specified.");
        }

        float manufactureCost = parameter.getAsFloat();
        logger.info("Executing FILTER_GREATER_THAN_MANUFACTURE_COST — threshold=" + manufactureCost);

        List<Product> result = collectionManager.getCollection().stream()
                .filter(p -> p.getManufactureCost() > manufactureCost)
                .sorted()
                .collect(Collectors.toList());

        logger.info("FILTER_GREATER_THAN_MANUFACTURE_COST result: " + result.size() + " elements found");

        if (result.isEmpty()) {
            return new Response(true, "No elements found with manufactureCost > " + manufactureCost);
        }
        return new Response(true, "filter_greater_than_manufacture_cost", result);
    }
}