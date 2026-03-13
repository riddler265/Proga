import collection.Manager;
import commandManager.CommandManager;
import jsonmanager.JsonManager;

import java.io.File;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //collectionManager
        Manager collectionManager = new Manager();

        //commandManager
        CommandManager commandManager = new CommandManager(collectionManager);
        Scanner scanner = new Scanner(System.in);

        //jsonManager
        String path = System.getenv("PRODUCTS");
        if (path == null || path.isEmpty()) {
            System.out.println("The environment variable is not set!");
            System.exit(1);
        }

        File file = new File(path);
        JsonManager jsonManager = new JsonManager(file.getAbsolutePath());

        //programm
        while (true) {
            commandManager.execute(scanner.nextLine());
        }

    }
}