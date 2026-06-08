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

public class RemoveGreaterCommand implements Command {
    private static final Logger logger = Logger.getLogger(RemoveGreaterCommand.class.getName());
    private final CollectionManager cm;
    public RemoveGreaterCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_id");
        int id = param.getAsInt();
        Product target = cm.getProductById(id);
        if (target == null) return new Response(false, "response.error.not_found", new String[]{ String.valueOf(id) });
        // Удаляем только те, которые принадлежат этому пользователю и больше target
        List<Product> toRemove = cm.getCollection().stream()
                .filter(p -> p.compareTo(target) > 0 && login.equals(p.getOwnerLogin()))
                .collect(Collectors.toList());
        int removed = 0;
        for (Product p : toRemove) {
            if (cm.removeById(p.getId(), login)) removed++;
        }
        return new Response(true, "response.remove_greater.success", new String[]{ String.valueOf(removed) });
    }
}
