package commands;

import managers.AnnounceManager;
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
    protected final AnnounceManager announceManager = AnnounceManager.getInstance();

    public Command(CollectionManager collection) {
        this.collectionManager = collection;
    }

    protected void print(String key, String ... params) {
        announceManager.print(key, params);
    }

    protected void println(String key, String ... params) {
        announceManager.println(key, params);
    }

    protected String cTCL(String key, String ... params) {
        return announceManager.cTCL(key, params);
    }
    /**
     * Выполняет логику команды.
     * @param input Аргументы, переданные вместе с командой в одной строке.
     * @param scanner Объект класса {@link Scanner} для считывания дополнительных данных
     * в интерактивном режиме.
     */
    public abstract void execute(String input, Scanner scanner);
}
