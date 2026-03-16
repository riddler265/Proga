package history;

import java.util.Arrays;

public class History {

    //fields
    private final String[] history = new String[8];

    //get
    public String[] getHistory() {
        return Arrays.copyOf(history, history.length);
    }

    //add
    public void add(String command) {
        for (int i = 0; i < 7; i++) {
            history[i] = history[i + 1];
        }
        history[7] = command;
    }
}
