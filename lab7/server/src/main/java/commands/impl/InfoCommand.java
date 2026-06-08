package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;
import java.util.logging.Logger;

public class InfoCommand implements Command {
    private static final Logger logger = Logger.getLogger(InfoCommand.class.getName());
    private final CollectionManager cm;
    public InfoCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        String type = cm.getCollection().getClass().getSimpleName();
        String date = cm.getInitializationDate();
        String size = String.valueOf(cm.getCollection().size());
        return new Response(true, "collection.info", new String[]{ type, date, size });
    }
}
