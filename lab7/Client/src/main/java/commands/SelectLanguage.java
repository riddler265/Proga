package commands;

import exceptions.IncorrectInputException;
import localization.AnnounceManager;
import localization.Loc;
import util.ConsoleManager;

import java.util.Scanner;

public class SelectLanguage extends Command {

    public SelectLanguage(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        println("select.language");
        println("locales.info");
        String selected = consoleManager.isSystemReader() ? scanner.nextLine().trim() : input.trim();
        try {
            AnnounceManager.getInstance().setLocale(Loc.getLocale(selected));
            println("language.success");
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            if (consoleManager.isSystemReader()) execute(input, scanner);
        }
    }
}
