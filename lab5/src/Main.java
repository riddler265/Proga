import exceptions.RecursionException;
import managers.AnnounceManager;
import managers.CollectionManager;
import managers.CommandManager;
import managers.json.JsonManager;

import java.io.File;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/**
 * Консольное приложение.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println();

        File path = new File(System.getenv("PRODUCTS"));
        if (!path.exists() && path.isDirectory()) {
            AnnounceManager.getInstance().println("environment.error");
            System.exit(1);
        }

        CollectionManager collectionManager = new CollectionManager();
        JsonManager jsonManager = new JsonManager(path, collectionManager);
        jsonManager.load();
        CommandManager commandManager = new CommandManager(collectionManager, jsonManager);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                commandManager.execute(scanner.nextLine().trim(), scanner);
            } catch (RecursionException e) {
                System.out.println(e.getMessage());
            } finally {
                commandManager.setIsSystemReader(true);
            }
        }
    }
}