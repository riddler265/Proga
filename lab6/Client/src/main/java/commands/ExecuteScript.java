package commands;

import exceptions.RecursionException;
import util.ConsoleManager;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ExecuteScript extends Command {

    private final stack.Stack stack;

    public ExecuteScript(ConsoleManager consoleManager) {
        super(consoleManager);
        stack = consoleManager.getStack();
    }

    /**
     * Метод исполнения команды.
     * @param input ввод пользователя в консоль.
     * <p>
     * @param currentScanner объект класса {@link Scanner},
     * текущий источник чтения.
     * Исполняет команды через {@link ConsoleManager}, пока
     * в файл имеет строки.
     * </p>
     */
    @Override
    public void execute(String input, Scanner currentScanner) throws RecursionException {
        //fields
        File path = new File(System.getProperty("user.dir") + "/scripts",
                input.substring(input.lastIndexOf(" ") + 1));
        try (Scanner scanner = new Scanner(path)) {
            consoleManager.setIsSystemReader(false);
            stack.add(path);
            while (scanner.hasNextLine()) {
                consoleManager.execute(scanner.nextLine(), scanner);
            }
            println("execute.success", input.substring(input.lastIndexOf(" ") + 1));
            consoleManager.setIsSystemReader(true);
        } catch (IOException e) {
            println("execute.failure", e.getMessage(), path.getAbsolutePath());
        } finally {
            stack.remove(path);
        }
    }
}
