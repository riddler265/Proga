package commands;

import communication.Command;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class RemoveGreater extends commands.Command {

    public RemoveGreater(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder removeGreaterBuilder = new ClientRequestBuilder(Command.REMOVE_GREATER);
        try {
            removeGreaterBuilder.setIntParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(removeGreaterBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("no.element.id");
        }
    }
}
