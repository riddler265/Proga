package commands.subscription;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.DatabaseManager;
import network.Response;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UnsubscribeCommand implements Command {
    private static final Logger logger = Logger.getLogger(UnsubscribeCommand.class.getName());
    private final DatabaseManager db;
    public UnsubscribeCommand(DatabaseManager db) { this.db = db; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        if (param == null) return new Response(false, "response.error.no_id");
        int id = param.getAsInt();
        try {
            boolean ok = db.deleteSubscription(id, login);
            if (!ok) return new Response(false, "response.unsubscribe.not_found", new String[]{ String.valueOf(id) });
            return new Response(true, "response.unsubscribe.success", new String[]{ String.valueOf(id) });
        } catch (SQLException e) {
            logger.severe("Unsubscribe error: " + e.getMessage());
            return new Response(false, "response.error.db");
        }
    }
}
