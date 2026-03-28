import managers.CollectionManager;
import managers.CommandManager;
import managers.json.JsonManager;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Консольное приложение.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println();

        /**
         * Создание {@link CollectionManager}.
         */
        CollectionManager collectionManager = new CollectionManager();

        /**
         * Работа с json-файлом.
         */
        String path = System.getenv("PRODUCTS");
        if (path == null || path.isEmpty()) {
            System.out.println("Переменная окружения не установлена!");
            System.exit(1);
        }

        JsonManager jsonManager = new JsonManager(path, collectionManager);
        jsonManager.load();

        /**
         * <p>
         *     Создание {@link CommandManager} и {@link Scanner},
         *     который будет читать ввод в консоли.
         * </p>
         */
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager(collectionManager, jsonManager);

        /**
         * Запуск программы.
         */
        while (true) {
            commandManager.execute(scanner.nextLine().trim(), scanner);
        }
    }
}