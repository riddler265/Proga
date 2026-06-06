package commands;

import communication.Command;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Info extends commands.Command {

    public Info(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder infoBuilder = new ClientRequestBuilder(Command.INFO);
        toOutQueue(infoBuilder.buildSimpleRequest());
    }
}
