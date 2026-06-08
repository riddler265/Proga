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
    private final Map<String, commands.Command> commands = new HashMap<>();
    private final BlockingQueue<String> outQueue;

    private final stack.History history = new stack.History();
    private final stack.Stack stack = new stack.Stack();

    public ConsoleManager(BlockingQueue<String> outQueue) {
        this.outQueue = outQueue;

        commands.put("help",                                new Help(this));
        commands.put("info",                                new Info(this));
        commands.put("show",                                new Show(this));
        commands.put("history",                             new History(this, history));
        commands.put("clear",                               new Clear(this));
        commands.put("add",                                 new Add(this));
        commands.put("update_id",                           new UpdateId(this));
        commands.put("remove_by_id",                        new RemoveById(this));
        commands.put("execute_script",                      new ExecuteScript(this));
        commands.put("add_if_min",                          new AddIfMin(this));
        commands.put("remove_greater",                      new RemoveGreater(this));
        commands.put("remove_all_by_price",                 new RemoveAllByPrice(this));
        commands.put("filter_less_than_manufacture_cost",   new FilterLessThanManufactureCost(this));
        commands.put("filter_greater_than_manufacture_cost",new FilterGreaterThanManufactureCost(this));
        commands.put("language",                            new SelectLanguage(this));
        commands.put("exit",                                new Exit(this));
        commands.put("register",                            new Register(this));
        commands.put("login",                               new Login(this));
        commands.put("subscribe",                           new Subscribe(this));
        commands.put("unsubscribe",                         new Unsubscribe(this));
        commands.put("list_subscriptions",                  new ListSubscriptions(this));
    }

    public void toOutQueue(String request) { outQueue.add(request); }
    public stack.Stack getStack()          { return stack; }
    public boolean isSystemReader()        { return isSystemReader; }
    public void setIsSystemReader(boolean condition) { isSystemReader = condition; }

    public void execute(String input, Scanner scanner) throws RecursionException {
        if (input == null || input.isBlank()) return;

        // Разбиваем на часть-команду и аргумент по первому пробелу
        String[] parts   = input.split(" ", 2);
        String cmdToken  = parts[0].trim();            // всё до пробела
        String argToken  = parts.length > 1 ? parts[1].trim() : cmdToken;

        // 1. Ищем команду целиком (update_id, remove_by_id, list_subscriptions, ...)
        if (commands.containsKey(cmdToken)) {
            commands.get(cmdToken).execute(argToken, scanner);
            history.add(cmdToken);
            return;
        }

        // 2. Команда не найдена — пробуем отрезать суффикс после последнего _
        //    Например: "unsubscribe_id" -> "unsubscribe" + "id" (id игнорируем, берём argToken)
        //              "unsubscribe_5"  -> "unsubscribe" + "5"  (берём "5" как аргумент)
        int lastUnderscore = cmdToken.lastIndexOf('_');
        while (lastUnderscore > 0) {
            String subCmd = cmdToken.substring(0, lastUnderscore);
            String subArg = cmdToken.substring(lastUnderscore + 1);

            if (commands.containsKey(subCmd)) {
                // Если после пробела был явный аргумент — используем его,
                // иначе берём суффикс после _ как аргумент
                String finalArg = parts.length > 1 ? argToken : subArg;
                commands.get(subCmd).execute(finalArg, scanner);
                history.add(subCmd);
                return;
            }
            lastUnderscore = cmdToken.lastIndexOf('_', lastUnderscore - 1);
        }

        // 3. Ничего не нашли
        if (isSystemReader) {
            AnnounceManager.getInstance().println("valid_commands");
        }
    }
}
