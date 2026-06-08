package commands;

import com.google.gson.JsonObject;
import enums.Commands;
import util.ConsoleManager;

import java.util.Scanner;

public class Register extends Command {

    public Register(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        boolean isSys = consoleManager.isSystemReader();

        print("enter.login");
        String login = isSys ? scanner.nextLine().trim() : input.trim();

        print("enter.password");
        String password = isSys ? scanner.nextLine().trim() : "";

        JsonObject creds = new JsonObject();
        creds.addProperty("login", login);
        creds.addProperty("password", password);

        JsonObject req = new JsonObject();
        req.addProperty("command", Commands.REGISTER.getName());
        req.add("parameter", creds);

        toOutQueue(req);
    }
}
