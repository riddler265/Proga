package managers;

import commands.*;
import exceptions.RecursionException;
import managers.json.JsonManager;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс менеджера команд.
 * Отвечает за инициализацию и исполнение.
 */
public class CommandManager {

    //fields
    //collections
    private final Map<String, Command> commands = new HashMap<>();
    private final stack.History history = new stack.History();
    private final stack.Stack stack = new stack.Stack();
    //announceManager
    private final AnnounceManager announceManager = AnnounceManager.getInstance();
    //scanner
    private boolean isSystemReader = true;

    /**
     * Конструктор. Инициализация команд.
     * @param collection {@link CollectionManager}, передается командам.
     * @param jsonManager {@link JsonManager} передается команде {@link Save}.
     */
    public CommandManager(CollectionManager collection, JsonManager jsonManager) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
        commands.put("show", new Show(collection));
        commands.put("history", new History(collection, history));
        commands.put("clear", new Clear(collection));
        commands.put("add", new Add(collection, this));
        commands.put("update id", new UpdateId(collection, this));
        commands.put("remove_by_id", new RemoveById(collection));
        commands.put("execute_script", new Execute(collection, this));
        commands.put("add_if_min", new AddIfMin(collection, this));
        commands.put("remove_greater", new RemoveGreater(collection, this));
        commands.put("remove_all_by_price", new RemoveAllByPrice(collection));
        commands.put("filter_less_than_manufacture_cost", new FilterLessThanManufactureCost(collection));
        commands.put("filter_greater_than_manufacture_cost", new FilterGreaterThanManufactureCost(collection));
        commands.put("save", new Save(collection, jsonManager));
        commands.put("language", new SelectLanguage(collection));
    }

    /**
     * Устанавливает тип сканера.
     * <p>
     * @param condition работает сейчас {@link Scanner} System.in
     * или File.
     */
    public void setIsSystemReader(boolean condition) {
        if (condition) isSystemReader = true;
        else isSystemReader = false;
    }

    public AnnounceManager getAnnounceManager() {
        return announceManager;
    }

    public stack.Stack getStack() {
        return stack;
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

    /**
     * Исполнение полученной команды, обработка входящих данных.
     * @param input входящие данные.
     * @param scanner откуда данные берутся.
     */
    public void execute(String input, Scanner scanner) throws RecursionException {

        //clear
        String command = input.replaceAll("\\s+\\S+$", "");
        String argument = input.substring(input.lastIndexOf(" ") + 1);

        //execute
        if (commands.containsKey(command)) {
            commands.get(command).execute(argument, scanner);
            history.add(command);
        } else if (isSystemReader){
            announceManager.println("unknown.command");
        } else {
            return;
        }
    }
}
