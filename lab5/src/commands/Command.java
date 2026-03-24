package commands;

import collectionManager.CollectionManager;

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

    /**
     * Переменная, хранящая {@link CollectionManager} менеджера коллекции.
     */
    protected final CollectionManager collection;

    /**
     * Конструктор.
     * @param collection объект класса {@link CollectionManager}.
     */
    public Command(CollectionManager collection) {
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
