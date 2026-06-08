package commands;

import enums.Commands;
import util.ClientRequestBuilder;
import util.ConsoleManager;
import util.NumbParser;

import java.util.Scanner;

public class Unsubscribe extends Command {

    public Unsubscribe(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        try {
            int id = NumbParser.parseInt(input);
            ClientRequestBuilder builder = new ClientRequestBuilder(Commands.UNSUBSCRIBE);
            builder.setIntParameter(String.valueOf(id));
            toOutQueue(builder.buildSimpleRequest());
        } catch (Exception e) {
            println("incorrectInput.e.no.conditions");
        }
    }
}
