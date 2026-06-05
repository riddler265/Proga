package commands;

import commands.impl.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import enums.Commands;
import json.JsonManager;
import managers.CollectionManager;
import network.Response;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Модуль обработки команд.
 * Регистрирует все команды в Map и делегирует выполнение нужному обработчику.
 */
public class CommandProcessor {

    private static final Logger logger = Logger.getLogger(CommandProcessor.class.getName());

    private final CollectionManager collectionManager;
    private final Map<Commands, Command> registry = new EnumMap<>(Commands.class);

    public CommandProcessor(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        registerCommands();
    }

    /**
     * Регистрация всех команд.
     */
    private void registerCommands() {
        registry.put(Commands.INFO,                              new InfoCommand(collectionManager));
        registry.put(Commands.SHOW,                             new ShowCommand(collectionManager));
        registry.put(Commands.ADD,                              new AddCommand(collectionManager));
        registry.put(Commands.UPDATE_ID,                        new UpdateIdCommand(collectionManager));
        registry.put(Commands.REMOVE_BY_ID,                     new RemoveByIdCommand(collectionManager));
        registry.put(Commands.CLEAR,                            new ClearCommand(collectionManager));
        registry.put(Commands.ADD_IF_MIN,                       new AddIfMinCommand(collectionManager));
        registry.put(Commands.REMOVE_GREATER,                   new RemoveGreaterCommand(collectionManager));
        registry.put(Commands.REMOVE_ALL_BY_PRICE,              new RemoveAllByPriceCommand(collectionManager));
        registry.put(Commands.FILTER_LESS_THAN_MANUFACTURE_COST,    new FilterLessThanManufactureCostCommand(collectionManager));
        registry.put(Commands.FILTER_GREATER_THAN_MANUFACTURE_COST, new FilterGreaterThanManufactureCostCommand(collectionManager));

        logger.info("CommandProcessor initialized: " + registry.size() + " commands registered");
    }

    /**
     * Разбирает JSON-запрос и делегирует выполнение нужной команде.
     */
    public Response process(String request) {
        Commands command;

        try {
            command = JsonManager.getCommand(request);
        } catch (Exception e) {
            logger.warning("Failed to parse command from request: " + e.getMessage());
            return new Response(false, "Невозможно распознать команду.");
        }

        logger.info("Received command: " + command);

        Command handler = registry.get(command);

        if (handler == null) {
            logger.warning("No handler registered for command: " + command);
            return new Response(false, "Команда '" + command + "' не поддерживается сервером.");
        }

        // Парсим части запроса
        JsonObject jProduct     = JsonManager.getProduct(request);
        JsonObject jCoordinates = JsonManager.getNestedObject(jProduct, "coordinates");
        JsonObject jPerson      = JsonManager.getNestedObject(jProduct, "owner");
        JsonElement parameter   = JsonManager.getParameter(request);

        logger.fine("Parsed request — product=" + (jProduct != null) +
                    ", coordinates=" + (jCoordinates != null) +
                    ", person=" + (jPerson != null) +
                    ", parameter=" + parameter);

        try {
            Response response = handler.execute(jProduct, jCoordinates, jPerson, parameter);
            logger.info("Command " + command + " completed — success=" + response.isSuccess());
            return response;
        } catch (Exception e) {
            logger.severe("Unexpected error executing command " + command + ": " + e.getMessage());
            return new Response(false, "Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
