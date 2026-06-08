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

public class RemoveAllByPriceCommand implements Command {
    private static final Logger logger = Logger.getLogger(RemoveAllByPriceCommand.class.getName());
    private final CollectionManager cm;
    public RemoveAllByPriceCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_price");
        float price = param.getAsFloat();
        List<Product> toRemove = cm.getCollection().stream()
                .filter(p -> p.getPrice() != null && Math.abs(p.getPrice() - price) < 0.0001f
                          && login.equals(p.getOwnerLogin()))
                .collect(Collectors.toList());
        int removed = 0;
        for (Product p : toRemove) {
            if (cm.removeById(p.getId(), login)) removed++;
        }
        return new Response(true, "response.remove_all_by_price.success",
                new String[]{ String.valueOf(price), String.valueOf(removed) });
    }
}
