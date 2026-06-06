package commands;

import communication.Command;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class FilterLessThanManufactureCost extends commands.Command {

    public FilterLessThanManufactureCost(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder filterLessThanManufactureCostBuilder = new ClientRequestBuilder(Command.FILTER_LESS_THAN_MANUFACTURE_COST);
        try {
            filterLessThanManufactureCostBuilder.setFloatParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(filterLessThanManufactureCostBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("remove.by.price.condition");
        }
    }
}
