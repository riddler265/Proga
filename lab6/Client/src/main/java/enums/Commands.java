package enums;

import exceptions.IncorrectInputException;
import validators.Validator;

public enum Commands {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    ADD("add"),
    UPDATE_ID("update_id"),
    REMOVE_BY_ID("remove_by_id"),
    CLEAR("clear"),
    SAVE("save"),
    EXECUTE_SCRIPT("execute_script"),
    EXIT("exit"),
    ADD_IF_MIN("add_if_min"),
    REMOVE_GREATER("remove_greater"),
    HISTORY("history"),
    REMOVE_ALL_BY_PRICE("remove_all_by_price"),
    FILTER_LESS_THAN_MANUFACTURE_COST("filter_less_than_manufacture_cost"),
    FILTER_GREATER_THAN_MANUFACTURE_COST("filter_greater_than_manufacture_cost");

    private final String name;
    private static final String valid_commands = "valid_commands";

    // Конструктор enum (всегда private по умолчанию)
    Commands(String name) {
        this.name = name;
    }

    public static Commands getCommand(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(valid_commands);
        }

        String trimmedInput = input.trim();

        for (Commands Command : values()) {
            if (Command.name.equalsIgnoreCase(trimmedInput)) {
                return Command;
            }
        }

        throw new IncorrectInputException(valid_commands);
    }
}
