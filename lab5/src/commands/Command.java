package commands;

import collection.Manager;
import commandManager.CommandManager;
import exceptions.IncorrectInputException;

import java.util.Scanner;

/**
 * Абстрактный класс команд.
 * <p>
 *     Каждая команда имеет доступ к коллекции через
 *     {@link Manager} и абстрактный метод
 *     {@link #execute(String, Scanner)} для исполнения логики.
 * </p>
 *
 */
public abstract class Command {

    /**
     * Переменная, хранящая {@link Manager} менеджера коллекции.
     */
    protected final Manager collection;

    /**
     * Конструктор.
     * @param collection объект класса {@link Manager}.
     */
    public Command(Manager collection) {
        this.collection = collection;
    }

    /**
     * Абстрактный метод выполнения программы.
     * @param input ввод пользователя в консоль.
     * <p>
     *     @param scanner объект класса {@link Scanner},
     *     текущий источник чтения.
     * </p>
     */
    public abstract void execute(String input, Scanner scanner);
}
