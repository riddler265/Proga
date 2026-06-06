package util;

import commands.*;
import exceptions.RecursionException;
import localization.AnnounceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConsoleManager {

    private boolean isSystemReader = true;
    private final Map<String, Command> commands = new HashMap<>();
    private final BlockingQueue<String> outQueue;

    private final stack.History history = new stack.History();
    private final stack.Stack stack = new stack.Stack();


    public ConsoleManager(BlockingQueue<String> outQueue) {
        this.outQueue = outQueue;

        commands.put("help", new Help(this));
        commands.put("info", new Info(this));
        commands.put("show", new Show(this));
        commands.put("history", new History(this, history));
        commands.put("clear", new Clear(this));
        commands.put("add", new Add(this));
        commands.put("update_id", new UpdateId(this));
        commands.put("remove_by_id", new RemoveById(this));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("add_if_min", new AddIfMin(this));
        commands.put("remove_greater", new RemoveGreater(this));
        commands.put("remove_all_by_price", new RemoveAllByPrice(this));
        commands.put("filter_less_than_manufacture_cost", new FilterLessThanManufactureCost(this));
        commands.put("filter_greater_than_manufacture_cost", new FilterGreaterThanManufactureCost(this));
        commands.put("language", new SelectLanguage(this));
        commands.put("exit", new Exit(this));
    }

    public void toOutQueue(String request) {
        outQueue.add(request);
    }

    public stack.Stack getStack() {
        return stack;
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

        String command = input.replaceAll("\\s+\\S+$", "");
        String argument = input.substring(input.lastIndexOf(" ") + 1);

        if (commands.containsKey(command)) {
            commands.get(command).execute(argument, scanner);
            history.add(command);
        } else if (isSystemReader){
            AnnounceManager.getInstance().println("valid_commands");
        } else {
            return;
        }

    }
}
