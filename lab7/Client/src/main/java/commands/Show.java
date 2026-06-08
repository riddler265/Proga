package commands;

import enums.Commands;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Show extends Command {

    public Show(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder showBuilder = new ClientRequestBuilder(Commands.SHOW);
        toOutQueue(showBuilder.buildSimpleRequest());
    }
}
