package commands;

import util.ConsoleManager;

import java.util.Scanner;

public class Help extends Command {

    public Help(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        String valid_commands = "valid_commands";
        println(valid_commands);
    }
}
