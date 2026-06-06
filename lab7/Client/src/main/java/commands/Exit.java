package commands;

import util.ConsoleManager;

import java.util.Scanner;

public class Exit extends Command {

    public Exit(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        println("goodbye");
        System.exit(1);
    }
}
