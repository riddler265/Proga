package exceptions;

import humans.Human;

public class DontHaveEnoughMoneyException extends Exception {
    public DontHaveEnoughMoneyException(Human person) {
        super(person.getName() + " don`t have enough money.");
    }
}