package server;

import java.util.logging.*;

/**
 * Настройка Java Util Logging.
 */
public class LoggerConfig {

    public static void setup() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);

        // Убираем дефолтные хендлеры
        for (Handler h : rootLogger.getHandlers()) {
            rootLogger.removeHandler(h);
        }

        // Консольный хендлер
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new SimpleFormatter() {
            private static final String FORMAT = "[%1$tF %1$tT] [%2$s] %3$s %n";
            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(FORMAT,
                        new java.util.Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });
        rootLogger.addHandler(consoleHandler);
    }
}
