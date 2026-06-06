package commands;

import exceptions.IncorrectInputException;
import localization.AnnounceManager;
import localization.Loc;
import util.ConsoleManager;

import java.util.Scanner;

public class SelectLanguage extends Command{

    public SelectLanguage(ConsoleManager consoleManager) {
        super(consoleManager);
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
