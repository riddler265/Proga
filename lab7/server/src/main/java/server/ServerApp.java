package server;

import auth.UserManager;
import db.DatabaseManager;
import managers.CollectionManager;
import network.ConnectionAcceptor;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerApp {

    public static final Logger logger = Logger.getLogger(ServerApp.class.getName());
    public static final int PORT = 9090;

    public static void main(String[] args) {
        try {
            LoggerConfig.setup();

            logger.info("=== Server starting on port " + PORT + " ===");

            // БД
            DatabaseManager db = new DatabaseManager();
            UserManager userManager = new UserManager(db);
            CollectionManager collectionManager = new CollectionManager(db);
            collectionManager.loadCollection();

            logger.info("Collection loaded: " + collectionManager.getCollection().size() + " elements");

            // Хук завершения — закрываем соединение с БД
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutdown: closing DB connection...");
                db.close();
                logger.info("Server stopped.");
            }));

            // Запуск
            try {
                ConnectionAcceptor acceptor = new ConnectionAcceptor(PORT, collectionManager, userManager);
                acceptor.run();
            } catch (IOException e) {
                logger.severe("Failed to start server: " + e.getMessage());
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
