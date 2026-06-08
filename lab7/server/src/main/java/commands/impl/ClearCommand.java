package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;
import java.util.logging.Logger;

public class ClearCommand implements Command {
    private static final Logger logger = Logger.getLogger(ClearCommand.class.getName());
    private final CollectionManager cm;
    public ClearCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        // Удаляем только продукты этого пользователя
        int removed = cm.clearByOwner(login);
        return new Response(true, "response.clear.success", new String[]{ String.valueOf(removed) });
    }
}
