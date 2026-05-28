package commands;

import enums.Commands;
import util.ConsoleManager;

import java.util.Scanner;

public class Show extends Command {

    public Show(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        buildSimpleRequest(Commands.SHOW);
    }
}
