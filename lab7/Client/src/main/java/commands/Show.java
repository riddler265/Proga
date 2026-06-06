package commands;

import communication.Command;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Show extends commands.Command {

    public Show(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder showBuilder = new ClientRequestBuilder(Command.SHOW);
        toOutQueue(showBuilder.buildSimpleRequest());
    }
}
