package commands;

import communication.Command;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class RemoveById extends commands.Command {

    public RemoveById(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder removeByIdBuilder = new ClientRequestBuilder(Command.REMOVE_BY_ID);
        try {
            removeByIdBuilder.setIntParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(removeByIdBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("no.element.id");
        }
    }
}
