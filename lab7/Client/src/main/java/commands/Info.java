package commands;

import enums.Commands;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Info extends Command {

    public Info(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder infoBuilder = new ClientRequestBuilder(Commands.INFO);
        toOutQueue(infoBuilder.buildSimpleRequest());
    }
}
