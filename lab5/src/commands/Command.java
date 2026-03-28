package commands;

import managers.CollectionManager;

import java.util.Scanner;

/**
 * Абстрактный класс команд.
 * <p>
 *     Каждая команда имеет доступ к коллекции через
 *     {@link CollectionManager} и абстрактный метод
 *     {@link #execute(String, Scanner)} для исполнения логики.
 * </p>
 *
 */
public abstract class Command {

    protected final CollectionManager collectionManager;

    public Command(CollectionManager collection) {
        this.collectionManager = collection;
    }

    /**
     * Выполняет логику команды.
     * @param input Аргументы, переданные вместе с командой в одной строке.
     * @param scanner Объект класса {@link Scanner} для считывания дополнительных данных
     * в интерактивном режиме.
     */
    public abstract void execute(String input, Scanner scanner);
}
