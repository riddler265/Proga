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

public class FilterLessThanManufactureCostCommand implements Command {
    private static final Logger logger = Logger.getLogger(FilterLessThanManufactureCostCommand.class.getName());
    private final CollectionManager cm;
    public FilterLessThanManufactureCostCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_manufacture_cost");
        float cost = param.getAsFloat();
        List<Product> result = cm.getCollection().stream()
                .filter(p -> p.getManufactureCost() < cost).sorted().collect(Collectors.toList());
        if (result.isEmpty()) return new Response(true, "response.filter_less.empty", new String[]{ String.valueOf(cost) });
        return new Response(true, "response.filter_less.success", new String[0], result);
    }
}
