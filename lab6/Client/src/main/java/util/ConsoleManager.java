package util;

import enums.Commands;
import exceptions.RecursionException;
import validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConsoleManager {

    private boolean isSystemReader = true;
    private final Map<String, Validator> validators = new HashMap<>();
    private final BlockingQueue<String> outQueue;


    public ConsoleManager(BlockingQueue<String> outQueue) {
        this.outQueue = outQueue;

        validators.put("help", new Help_Validator(this));
        validators.put("exit", new Exit_Validator(this));
        validators.put("info", new Info_Validator(this));
        validators.put("show", new Show_Validator(this));
        validators.put("history", new History_Validator(this));
        validators.put("clear", new Clear_Validator(this));
        validators.put("add", new Add_Validator(this));
        validators.put("update id", new Update_Id_Validator(this));
        validators.put("remove_by_id", new Remove_By_Id_Validator(this));
        validators.put("execute_script", new Execute_Script_Validator(this));
        validators.put("add_if_min", new Add_If_Min_Validator(this));
        validators.put("remove_greater", new Remove_Greater_Validator(this));
        validators.put("remove_all_by_price", new Remove_All_By_Price_Validator(this));
        validators.put("filter_less_than_manufacture_cost", new Filter_Less_Than_Manufacture_Cost_Validator(this));
        validators.put("filter_greater_than_manufacture_cost", new Filter_Greater_Than_Manufacture_Cost_Validator(this));
        validators.put("save", new Save_Validator(this, jsonManager));
        validators.put("language", new Select_Language_Validator(this));
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
