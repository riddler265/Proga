package commands;

import enums.Commands;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class ListSubscriptions extends Command {

    public ListSubscriptions(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder builder = new ClientRequestBuilder(Commands.LIST_SUBSCRIPTIONS);
        toOutQueue(builder.buildSimpleRequest());
    }
}
