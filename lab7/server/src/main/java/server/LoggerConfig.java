package server;

import java.util.logging.*;

public class LoggerConfig {

    public static void setup() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);

        for (Handler h : rootLogger.getHandlers()) {
            rootLogger.removeHandler(h);
        }

        // Заглушаем JDBC-драйвер PostgreSQL (он очень болтливый на FINEST/FINE)
        Logger.getLogger("org.postgresql").setLevel(Level.WARNING);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
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
