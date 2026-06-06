package server;

import managers.CollectionManager;
import managers.FileManager;
import network.ConnectionAcceptor;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Точка входа серверного приложения.
 */
public class ServerApp {

    public static final Logger logger = Logger.getLogger(ServerApp.class.getName());
    public static final int PORT = 9090;

    public static void main(String[] args) {
        try {
            // Настройка логгера
            LoggerConfig.setup();

            // Путь к файлу коллекции — из аргумента или по умолчанию
            String filePath = System.getenv("COLLECTION_FILE");
            if (filePath == null || filePath.isBlank()) {
                filePath = "collection.json"; // дефолт если переменная не задана
                logger.warning("COLLECTION_FILE env variable not set, using default: " + filePath);
            } else {
                logger.info("COLLECTION_FILE: " + filePath);
            }

            logger.info("=== Server starting on port " + PORT + " ===");
            logger.info("Collection file: " + filePath);

            // Инициализация менеджеров
            FileManager fileManager = new FileManager(filePath);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            collectionManager.loadCollection();

            // Хук для сохранения при завершении
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutdown hook triggered — saving collection...");
                collectionManager.saveCollection();
                logger.info("Collection saved. Server stopped.");
            }));

            // Запуск сервера
            try {
                ConnectionAcceptor acceptor = new ConnectionAcceptor(PORT, collectionManager);
                acceptor.run(); // однопоточный режим
            } catch (IOException e) {
                logger.severe("Failed to start server: " + e.getMessage());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
