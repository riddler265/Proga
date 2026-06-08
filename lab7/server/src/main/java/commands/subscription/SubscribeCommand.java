package commands.subscription;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.DatabaseManager;
import network.Response;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SubscribeCommand implements Command {
    private static final Logger logger = Logger.getLogger(SubscribeCommand.class.getName());
    private final DatabaseManager db;
    public SubscribeCommand(DatabaseManager db) { this.db = db; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        // param: {"field":"price","operator":"<","threshold":100.0}
        if (param == null || !param.isJsonObject()) return new Response(false, "response.subscribe.invalid");
        JsonObject p = param.getAsJsonObject();
        String field    = p.has("field")     ? p.get("field").getAsString()     : null;
        String operator = p.has("operator")  ? p.get("operator").getAsString()  : null;
        float  threshold = p.has("threshold") ? p.get("threshold").getAsFloat() : 0f;
        if (field == null || operator == null) return new Response(false, "response.subscribe.invalid");
        try {
            int id = db.addSubscription(login, field, operator, threshold);
            return new Response(true, "response.subscribe.success",
                    new String[]{ String.valueOf(id), field, operator, String.valueOf(threshold) });
        } catch (SQLException e) {
            logger.severe("Subscribe error: " + e.getMessage());
            return new Response(false, "response.error.db");
        }
    }
}
