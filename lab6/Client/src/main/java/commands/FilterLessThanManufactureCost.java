package commands;

import enums.Commands;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class FilterLessThanManufactureCost extends Command {

    public FilterLessThanManufactureCost(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder filterLessThanManufactureCostBuilder = new ClientRequestBuilder(Commands.FILTER_LESS_THAN_MANUFACTURE_COST);
        try {
            filterLessThanManufactureCostBuilder.setFloatParameter(input.substring(input.lastIndexOf(" ") + 1));
            toOutQueue(filterLessThanManufactureCostBuilder.buildSimpleRequest());
        } catch (IncorrectInputException e) {
            println("remove.by.price.condition");
        }
    }
}
