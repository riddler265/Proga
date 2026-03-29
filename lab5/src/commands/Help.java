package commands;

import managers.CollectionManager;
import java.util.Scanner;

/**
 * <p>
 *     Команда, выводящая список
 *     всех доступных команд.
 * </p>
 */
public class Help extends Command{
    //fields
    private final static String commands = """
            
            help - вывести справку по доступным командам.\
            
            info - вывести информацию о коллекции.\
            
            show - вывести все элементы коллекции.\
            
            add {element} - добавить новый элемент в коллекцию.\
            
            update id {element} - обновить значение элемента коллекции, id которого равен заданному.\
            
            remove_by_id id - удалить элемент из коллекции по его id.\
            
            clear - очистить коллекцию.\
            
            save - сохранить коллекцию в файл.\
            
            execute_script file_name - считать и исполнить скрипт из указанного файла.\
            
            exit - завершить программу (без сохранения в файл).\
            
            add_if_min {element} - добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.\
            
            remove_greater {element} - удалить из коллекции все элементы, превышающие заданный.\
            
            history - вывести последние 8 команд.\
            
            remove_all_by_price price - удалить из коллекции все элементы, значение поля price которого эквивалентно заданному.\
            
            filter_less_than_manufacture_cost manufactureCost - вывести элементы, значение поля manufactureCost которых меньше заданного.\
            
            filter_greater_than_manufacture_cost manufactureCost - вывести элементы, значение поля manufactureCost которых больше заданного.
            """;

    //constructor
    public Help(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(commands);
    }
}
