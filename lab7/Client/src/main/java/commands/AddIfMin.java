package commands;

import enums.Commands;
import exceptions.ExecuteException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class AddIfMin extends Add {

    public AddIfMin(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = consoleManager.isSystemReader();
        addBuilder = new ClientRequestBuilder(Commands.ADD_IF_MIN);

        try {
            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);

            toOutQueue(addBuilder.buildRequest());
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }

    }

}
