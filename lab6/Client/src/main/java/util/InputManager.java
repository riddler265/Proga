package util;

import enums.Commands;
import exceptions.RecursionException;
import validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputManager {

    private boolean isSystemReader = true;
    private final Map<String, Validator> validators = new HashMap<>();

    public InputManager() {
        validators.put("help", new Help_Validator(collection));
        validators.put("exit", new Exit_Validator(collection));
        validators.put("info", new Info_Validator(collection));
        validators.put("show", new Show_Validator(collection));
        validators.put("history", new History_Validator(collection, history));
        validators.put("clear", new Clear_Validator(collection));
        validators.put("add", new Add_Validator(collection, this));
        validators.put("update id", new Update_Id_Validator(collection, this));
        validators.put("remove_by_id", new Remove_By_Id_Validator(collection));
        validators.put("execute_script", new Execute_Script_Validator(collection, this));
        validators.put("add_if_min", new Add_If_Min_Validator(collection, this));
        validators.put("remove_greater", new Remove_Greater_Validator(collection, this));
        validators.put("remove_all_by_price", new Remove_All_By_Price_Validator(collection));
        validators.put("filter_less_than_manufacture_cost", new Filter_Less_Than_Manufacture_Cost_Validator(collection));
        validators.put("filter_greater_than_manufacture_cost", new Filter_Greater_Than_Manufacture_Cost_Validator(collection));
        validators.put("save", new Save_Validator(collection, jsonManager));
        validators.put("language", new Select_Language_Validator(collection));
    }

    /**
     * Устанавливает тип сканера.
     * <p>
     * @param condition работает сейчас {@link Scanner} System.in
     * или File.
     */
    public void setIsSystemReader(boolean condition) {
        isSystemReader = condition;
    }

    /**
     * <p>
     * Метод, возвращающий true, если работает
     * {@link Scanner} System.in, и false, если
     * File.
     * @return булево значение.
     */
    public boolean isSystemReader() {
        return  isSystemReader;
    }

    public void execute(String input, Scanner scanner) throws RecursionException {

        String command_type = input.replaceAll("\\s+\\S+$", "");
        String argument = input.substring(input.lastIndexOf(" ") + 1);

        try {
            Commands command = Commands.getCommand(command_type);
            command.execute();
        } catch ()

    }
}
