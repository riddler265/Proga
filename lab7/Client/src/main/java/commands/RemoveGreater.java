package commands;

import enums.Commands;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class RemoveGreater extends  Command{

    public RemoveGreater(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder removeGreaterBuilder = new ClientRequestBuilder(Commands.REMOVE_GREATER);
        try {
            removeGreaterBuilder.setIntParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(removeGreaterBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("no.element.id");
        }
    }
}
