package commands;

import com.google.gson.JsonObject;
import enums.Commands;
import util.ConsoleManager;
import util.Session;

import java.util.Scanner;

public class Login extends Command {

    public Login(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        boolean isSys = consoleManager.isSystemReader();

        print("enter.login");
        String login = isSys ? scanner.nextLine().trim() : input.trim();

        print("enter.password");
        String password = isSys ? scanner.nextLine().trim() : "";

        // Сохраняем в сессию сразу
        Session.getInstance().set(login, password);

        JsonObject creds = new JsonObject();
        creds.addProperty("login", login);
        creds.addProperty("password", password);

        // Собираем запрос вручную — без вложенности buildSimpleRequest
        JsonObject req = new JsonObject();
        req.addProperty("command", Commands.LOGIN.getName());
        req.add("parameter", creds);

        toOutQueue(req);
    }
}
