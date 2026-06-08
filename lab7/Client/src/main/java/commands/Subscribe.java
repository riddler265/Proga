package commands;

import com.google.gson.JsonObject;
import enums.Commands;
import util.ConsoleManager;

import java.util.Scanner;
import java.util.Set;

public class Subscribe extends Command {

    private static final Set<String> VALID_FIELDS = Set.of(
            "price", "manufactureCost", "coordX", "coordY", "height"
    );
    private static final Set<String> VALID_OPERATORS = Set.of("<", ">", "<=", ">=", "==");

    public Subscribe(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        boolean isSys = consoleManager.isSystemReader();

        // Поле
        println("subscribe.field.prompt");
        println("subscribe.field.options");
        String field = isSys ? scanner.nextLine().trim() : "price";
        if (!VALID_FIELDS.contains(field)) {
            println("subscribe.field.invalid");
            return;
        }

        // Оператор
        println("subscribe.operator.prompt");
        println("subscribe.operator.options");
        String operator = isSys ? scanner.nextLine().trim() : "<";
        if (!VALID_OPERATORS.contains(operator)) {
            println("subscribe.operator.invalid");
            return;
        }

        // Порог
        println("subscribe.threshold.prompt");
        float threshold;
        try {
            threshold = Float.parseFloat(isSys ? scanner.nextLine().trim() : "0");
        } catch (NumberFormatException e) {
            println("incorrectInput.e.no.conditions");
            return;
        }

        JsonObject param = new JsonObject();
        param.addProperty("field", field);
        param.addProperty("operator", operator);
        param.addProperty("threshold", threshold);

        JsonObject req = new JsonObject();
        req.addProperty("command", Commands.SUBSCRIBE.getName());
        req.add("parameter", param);
        toOutQueue(req);
    }
}
