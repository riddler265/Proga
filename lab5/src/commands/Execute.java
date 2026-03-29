package commands;

import exceptions.RecursionException;
import managers.CollectionManager;
import managers.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Команда исполнения файла.
 * <p>
 *     Поле типа {@link java.nio.file.Path} для хранения
 *     пути к файлу, ссылка на объект типа {@link CommandManager}
 *     для исполнения команд из файла.
 * </p>
 */
public class Execute extends Command{

    private final CommandManager commandManager;
    private final stack.Stack stack;

    //constructor
    public Execute(CollectionManager collectionManager, CommandManager commandManager) {
        super(collectionManager);
        this.commandManager = commandManager;
        this.stack = commandManager.getStack();
    }

    /**
     * Метод исполнения команды.
     * @param input ввод пользователя в консоль.
     * <p>
     * @param currentScanner объект класса {@link Scanner},
     * текущий источник чтения.
     * Исполняет команды через {@link CommandManager}, пока
     * в файл имеет строки.
     * </p>
     */
    @Override
    public void execute(String input, Scanner currentScanner) {
        //fields
        File path = new File("scripts", input.substring(input.lastIndexOf(" ") + 1));
        try (Scanner scanner = new Scanner(path)) {
            commandManager.setIsSystemReader(false);
            stack.add(path);
            while (scanner.hasNextLine()) {
                commandManager.execute(scanner.nextLine(), scanner);
            }
            System.out.println("Файл " + input.substring(input.lastIndexOf(" ") + 1) + " исполнен.\n");
            commandManager.setIsSystemReader(true);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + path.getAbsolutePath() + ".\n");
        } catch (IOException e) {
            System.out.println("Ошибка.");
        } catch (RecursionException e) {
            System.out.println(e.getMessage());
        }
    }
}
