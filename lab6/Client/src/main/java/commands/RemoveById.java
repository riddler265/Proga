package commands;

import enums.Commands;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class RemoveById extends Command {

    public RemoveById(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder removeByIdBuilder = new ClientRequestBuilder(Commands.REMOVE_BY_ID);
        try {
            removeByIdBuilder.setIntParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(removeByIdBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("no.element.id");
        }
    }
}
