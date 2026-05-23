package validators;

import json.ClientRequest;
import util.InputManager;
import java.util.Scanner;

public abstract class Validator {

    protected boolean isSystemReader;
    protected final InputManager inputManager;

    protected Validator(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public abstract ClientRequest execute(String input, Scanner scanner);
}
