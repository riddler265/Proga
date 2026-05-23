package validators;

import json.ClientRequest;

import java.util.Scanner;

public class HelpValidator extends Validator{

    @Override
    public ClientRequest execute(String input, Scanner scanner) {
        return new ClientRequest("asdasd", new Object());
    }
}
