package commands.subscription;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import db.DatabaseManager;
import network.Response;
import subscription.Subscription;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ListSubscriptionsCommand implements Command {
    private static final Logger logger = Logger.getLogger(ListSubscriptionsCommand.class.getName());
    private final DatabaseManager db;
    public ListSubscriptionsCommand(DatabaseManager db) { this.db = db; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson, JsonElement param, String login) {
        try {
            List<Subscription> subs = db.getSubscriptionsByUser(login);
            if (subs.isEmpty()) return new Response(true, "response.subscriptions.empty");
            String list = subs.stream()
                    .map(s -> "#" + s.getId() + ": " + s.getField() + " " + s.getOperator() + " " + s.getThreshold())
                    .collect(Collectors.joining("\n"));
            return new Response(true, "response.subscriptions.list", new String[]{ list });
        } catch (SQLException e) {
            logger.severe("ListSubscriptions error: " + e.getMessage());
            return new Response(false, "response.error.db");
        }
    }
}
