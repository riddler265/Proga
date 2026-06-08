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
    private final CollectionManager cm;
    public ShowCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        List<Product> sorted = cm.getCollection().stream().sorted().collect(Collectors.toList());
        if (sorted.isEmpty()) return new Response(true, "response.show.empty", new ArrayList<>());
        return new Response(true, "response.show.success", new String[]{ String.valueOf(sorted.size()) }, sorted);
    }
}
