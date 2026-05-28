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
        consoleManager.execute("show", scanner);
        println("write.id");
        ClientRequestBuilder rGRequest = new ClientRequestBuilder(Commands.REMOVE_GREATER);
        try {
            rGRequest.setIntParameter(scanner.nextLine());
            consoleManager.addToOutQueue(JsonManager.requestSerialization(rGRequest.buildSimpleRequest()));
        } catch (IncorrectInputException e) {
            println("no.element.id");
        }
    }
}
