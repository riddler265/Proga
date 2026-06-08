package commands;

import auth.UserManager;
import commands.impl.*;
import commands.subscription.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import enums.Commands;
import json.JsonManager;
import managers.CollectionManager;
import network.Response;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Обработчик команд.
 * Проверяет авторизацию перед каждой командой (кроме register/login).
 */
public class CommandProcessor {

    private static final Logger logger = Logger.getLogger(CommandProcessor.class.getName());

    private final CollectionManager collectionManager;
    private final UserManager userManager;
    private final Map<Commands, Command> registry = new EnumMap<>(Commands.class);

    /** Команды, доступные без авторизации. */
    private static final Set<Commands> PUBLIC_COMMANDS = Set.of(
            Commands.REGISTER, Commands.LOGIN
    );

    public CommandProcessor(CollectionManager collectionManager, UserManager userManager) {
        this.collectionManager = collectionManager;
        this.userManager = userManager;
        registerCommands();
    }

    private void registerCommands() {
        registry.put(Commands.INFO,                                  new InfoCommand(collectionManager));
        registry.put(Commands.SHOW,                                  new ShowCommand(collectionManager));
        registry.put(Commands.ADD,                                   new AddCommand(collectionManager));
        registry.put(Commands.UPDATE_ID,                             new UpdateIdCommand(collectionManager));
        registry.put(Commands.REMOVE_BY_ID,                         new RemoveByIdCommand(collectionManager));
        registry.put(Commands.CLEAR,                                 new ClearCommand(collectionManager));
        registry.put(Commands.ADD_IF_MIN,                            new AddIfMinCommand(collectionManager));
        registry.put(Commands.REMOVE_GREATER,                       new RemoveGreaterCommand(collectionManager));
        registry.put(Commands.REMOVE_ALL_BY_PRICE,                  new RemoveAllByPriceCommand(collectionManager));
        registry.put(Commands.FILTER_LESS_THAN_MANUFACTURE_COST,    new FilterLessThanManufactureCostCommand(collectionManager));
        registry.put(Commands.FILTER_GREATER_THAN_MANUFACTURE_COST, new FilterGreaterThanManufactureCostCommand(collectionManager));
        registry.put(Commands.SAVE,                                  new SaveCommand(collectionManager));
        // Auth
        registry.put(Commands.REGISTER,          new RegisterCommand(userManager));
        registry.put(Commands.LOGIN,             new LoginCommand(userManager));
        // Subscriptions
        registry.put(Commands.SUBSCRIBE,         new SubscribeCommand(collectionManager.getDb()));
        registry.put(Commands.UNSUBSCRIBE,       new UnsubscribeCommand(collectionManager.getDb()));
        registry.put(Commands.LIST_SUBSCRIPTIONS,new ListSubscriptionsCommand(collectionManager.getDb()));

        logger.info("CommandProcessor: " + registry.size() + " commands registered");
    }

    public Response process(String request) {
        Commands command;
        try {
            command = JsonManager.getCommand(request);
        } catch (Exception e) {
            return new Response(false, "response.error.unknown_command");
        }

        logger.info("Received command: " + command);

        // Авторизация
        if (!PUBLIC_COMMANDS.contains(command)) {
            String login    = JsonManager.getLogin(request);
            String password = JsonManager.getPassword(request);

            if (login == null || password == null) {
                return new Response(false, "response.error.not_authenticated");
            }
            if (!userManager.authenticate(login, password)) {
                return new Response(false, "response.error.wrong_credentials");
            }
        }

        Command handler = registry.get(command);
        if (handler == null) {
            return new Response(false, "response.error.unknown_command");
        }

        JsonObject jProduct     = JsonManager.getProduct(request);
        JsonObject jCoordinates = JsonManager.getNestedObject(jProduct, "coordinates");
        JsonObject jPerson      = JsonManager.getNestedObject(jProduct, "owner");
        JsonElement parameter   = JsonManager.getParameter(request);
        String login            = JsonManager.getLogin(request);

        try {
            Response response = handler.execute(jProduct, jCoordinates, jPerson, parameter, login);
            logger.info("Command " + command + " completed, success=" + response.isSuccess());
            return response;
        } catch (Exception e) {
            logger.severe("Error executing " + command + ": " + e.getMessage());
            return new Response(false, "response.error.internal", new String[]{ e.getMessage() });
        }
    }

    public CollectionManager getCollectionManager() { return collectionManager; }
    public UserManager getUserManager()             { return userManager; }
}
