package commands.impl;

import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import managers.CollectionManager;
import network.Response;
import java.util.logging.Logger;

/**
 * Команда save — принудительно перезагружает коллекцию из БД в память.
 * (Так как хранение в файле убрано, "сохранение" = синхронизация памяти с БД)
 */
public class SaveCommand implements Command {
    private static final Logger logger = Logger.getLogger(SaveCommand.class.getName());
    private final CollectionManager cm;
    public SaveCommand(CollectionManager cm) { this.cm = cm; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson,
                            JsonElement param, String login) {
        int sizeBefore = cm.getCollection().size();
        cm.loadCollection();
        int sizeAfter = cm.getCollection().size();
        logger.info("SAVE: collection reloaded from DB, " + sizeAfter + " elements");
        return new Response(true, "response.save.success",
                new String[]{ String.valueOf(sizeBefore), String.valueOf(sizeAfter) });
    }
}
