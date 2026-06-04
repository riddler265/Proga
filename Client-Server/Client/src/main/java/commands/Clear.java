package commands;

import enums.Commands;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class Clear extends Command{

    public Clear(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder clearBuilder = new ClientRequestBuilder(Commands.CLEAR);
        toOutQueue(clearBuilder.buildSimpleRequest());
    }
}
