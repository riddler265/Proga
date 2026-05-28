package commands;

import exceptions.IncorrectInputException;
import managers.AnnounceManager;
import managers.CollectionManager;
import model.enums.Loc;

import java.util.Scanner;

public class SelectLanguage extends Command{

    public SelectLanguage(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        print("available.languages");
        String selectedL = scanner.nextLine();
        try {
            AnnounceManager.getInstance().setLocale(Loc.getLocale(selectedL));
            AnnounceManager.getInstance().println("language.success");
        } catch (IncorrectInputException e) {
            System.out.println();
            execute(selectedL, scanner);
        }
    }
}
