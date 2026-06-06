package commands;

import communication.Command;
import exceptions.ExecuteException;
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
        addBuilder = new ClientRequestBuilder(Command.ADD_IF_MIN);

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
