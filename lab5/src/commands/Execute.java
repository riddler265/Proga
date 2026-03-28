package commands;

import managers.CollectionManager;
import managers.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
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

    //fields
    private File path;
    private final CommandManager commandManager;

    //constructor
    public Execute(CollectionManager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
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
        path = new File("scripts", input.substring(input.lastIndexOf(" ") + 1));
        try (Scanner scanner = new Scanner(path)) {
            commandManager.setIsSystemReader(false);
            while (scanner.hasNextLine()) {
                commandManager.execute(scanner.nextLine(), scanner);
            }
            commandManager.setIsSystemReader(true);
            System.out.println("Файл исполнен.\n");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + path.getAbsolutePath() + ".\n");
        }
    }
}
