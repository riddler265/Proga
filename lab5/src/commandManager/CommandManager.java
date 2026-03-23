package commandManager;

import collection.Manager;
import commands.*;
import jsonmanager.JsonManager;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс менеджера команд.
 * Отвечает за инициализацию и исполнение.
 */
public class CommandManager {

    //fields
    //collections
    private final Map<String, Command> commands = new HashMap<>();
    private final history.History history = new history.History();
    //scanner
    private boolean isSystemReader = true;

    //constructor
    public CommandManager(Manager collection, JsonManager jsonManager) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
        commands.put("show", new Show(collection));
        commands.put("history", new History(collection, history));
        commands.put("clear", new Clear(collection));
        commands.put("add", new Add(collection, this));
        commands.put("update id", new UpdateId(collection, this));
        commands.put("remove_by_id", new RemoveById(collection));
        commands.put("execute_file", new Execute(collection, this));
        commands.put("add_if_min", new AddIfMin(collection, this));
        commands.put("remove_greater", new RemoveGreater(collection, this));
        commands.put("remove_all_by_price", new RemoveAllByPrice(collection));
        commands.put("filter_less_than_manufacture_cost", new FilterLessThanManufactureCost(collection));
        commands.put("filter_greater_than_manufacture_cost", new FilterGreaterThanManufactureCost(collection));
        commands.put("save", new Save(collection, jsonManager));
    }

    //setters
    public void setIsSystemReader(boolean condition) {
        if (condition) isSystemReader = true;
        else  isSystemReader = false;
    }

    //getters
    public boolean isSystemReader() {
        return  isSystemReader;
    }

    /**
     * Исполнение полученной команды, обработка входящих данных.
     * @param input входящие данные.
     * @param scanner откуда данные берутся.
     */
    public void execute(String input, Scanner scanner) {
        if (commands.containsKey(input.replaceAll("\\s+\\S+$", ""))) {
            commands.get(input.replaceAll("\\s+\\S+$", "")).execute(input, scanner);
            history.add(input);
        } else if (isSystemReader){
            System.out.println("Unknown command. Write help.\n");
        } else {
            return;
        }
    }
}
