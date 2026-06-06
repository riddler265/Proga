package stack;

import java.util.Arrays;

/**
 * Класс, хранящий последние 8 команд без аргументов.
 */
public class History {

    //fields
    private final String[] history = new String[8];

    /**
     * Метод, возвращающий последние 8 команд.
     * @return {@link Arrays}[String].
     */
    public String[] getHistory() {
        return Arrays.copyOf(history, history.length);
    }

    /**
     * Метод добавления команды в историю.
     * @param command - команда
     */
    public void add(String command) {
        for (int i = 0; i < history.length - 1; i++) {
            history[i] = history[i + 1];
        }
        history[history.length - 1] = command;
    }
}
