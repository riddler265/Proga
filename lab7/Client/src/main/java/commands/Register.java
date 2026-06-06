package commands;

import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import localization.AnnounceManager;
import model.Person;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

public class Register extends Command {


    protected boolean isSystemReader;
    protected boolean alreadyRegistered;
    protected ClientRequestBuilder registerBuilder;

    public Register(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    protected void writePersonName(String input, Scanner scanner) throws ExecuteException {
        try {
            registerBuilder.setPersonName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePersonName(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    /*protected void writeBirthday(String input, Scanner scanner) {
        try {
            registerBuilder.setBirthday(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeBirthday(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeHeight(String input, Scanner scanner) throws ExecuteException{
        try {
            registerBuilder.setHeight(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeHeight(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePassportID(String input, Scanner scanner) {
        registerBuilder.setPassportID(input);
    }

    protected void writeHairColor(String input, Scanner scanner) {
        try {
            registerBuilder.setHairColor(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeHairColor(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }*/

    protected void writePassword(String input, Scanner scanner) {
        try {
            registerBuilder.setPassword(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePassword(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    //endregion

    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = consoleManager.isSystemReader();

        registerBuilder = new ClientRequestBuilder(communication.Command.REGISTER);
        alreadyRegistered = consoleManager.isAlreadyRegistered();

        try {
            if (!alreadyRegistered) {

                registerBuilder.setBirthday(LocalDateTime.now().format(Person.formatter));
                registerBuilder.setHeight("1.80");
                registerBuilder.setPassportID("sXXXXXX");

                toOutQueue(registerBuilder.buildRequest());

            } else AnnounceManager.getInstance().format("uset.already.registered");
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }

    }
}
