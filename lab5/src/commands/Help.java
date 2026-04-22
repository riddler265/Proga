package commands;

import managers.AnnounceManager;
import managers.CollectionManager;
import java.util.Scanner;

/**
 * <p>
 *     Команда, выводящая список
 *     всех доступных команд.
 * </p>
 */
public class Help extends Command{

    //constructor
    public Help(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        println("help.message");
    }
}
