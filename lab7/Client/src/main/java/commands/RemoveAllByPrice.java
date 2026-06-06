package commands;

import communication.Command;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class RemoveAllByPrice extends commands.Command {

    public RemoveAllByPrice(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder removeAllByPriceBuilder = new ClientRequestBuilder(Command.REMOVE_ALL_BY_PRICE);
        try {
            removeAllByPriceBuilder.setFloatParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(removeAllByPriceBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("remove.by.price.condition");
        }
    }
}
