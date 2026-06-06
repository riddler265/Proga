package commands;

import communication.Command;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class FilterGreaterThanManufactureCost extends commands.Command {

    public FilterGreaterThanManufactureCost(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder filterGreaterThanManufactureCost = new ClientRequestBuilder(Command.FILTER_GREATER_THAN_MANUFACTURE_COST);
        try {
            filterGreaterThanManufactureCost.setFloatParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(filterGreaterThanManufactureCost.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("remove.by.price.condition");
        }
    }
}
