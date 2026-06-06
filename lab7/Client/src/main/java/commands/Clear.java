package commands;

import communication.Command;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Clear extends commands.Command {

    public Clear(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder clearBuilder = new ClientRequestBuilder(Command.CLEAR);
        toOutQueue(clearBuilder.buildSimpleRequest());
    }
}
