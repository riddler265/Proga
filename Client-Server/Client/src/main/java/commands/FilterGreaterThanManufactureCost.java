package commands;

import enums.Commands;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class FilterGreaterThanManufactureCost extends Command {

    public FilterGreaterThanManufactureCost(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        ClientRequestBuilder rGRequest = new ClientRequestBuilder(Commands.FILTER_GREATER_THAN_MANUFACTURE_COST);
        try {
            rGRequest.setFloatParameter(input.substring(input.lastIndexOf(" ") + 1));
            consoleManager.addToOutQueue(JsonManager.requestSerialization(rGRequest.buildSimpleRequest()));
        } catch (IncorrectInputException e) {
            println("remove.by.price.condition");
        }
    }
}
