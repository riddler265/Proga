import collectionManager.CollectionManager;
import commandManager.CommandManager;
import jsonmanager.JsonManager;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println();

        //collectionManager
        CollectionManager collectionManager = new CollectionManager();

        //jsonManager
        String path = System.getenv("PRODUCTS");
        if (path == null || path.isEmpty()) {
            System.out.println("Переменная окружения не установлена!");
            System.exit(1);
        }

        JsonManager jsonManager = new JsonManager(path, collectionManager);
        jsonManager.load();

        //commandManager
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager(collectionManager, jsonManager);

        //programm
        while (true) {
            commandManager.execute(scanner.nextLine().trim(), scanner);
        }
    }
}