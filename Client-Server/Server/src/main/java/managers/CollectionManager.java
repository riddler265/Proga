package managers;

import model.Product;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * Менеджер коллекции.
 * Управляет PriorityQueue<Product>.
 * Все операции обработки — через Stream API с лямбда-выражениями.
 */
public class CollectionManager {

    private static final Logger logger = Logger.getLogger(CollectionManager.class.getName());

    private PriorityQueue<Product> collection;
    private final LocalDateTime initializationDate;
    private final FileManager fileManager;

    private Product greatestProduct;

    // История последних 6 команд
    private final LinkedList<String> history = new LinkedList<>();

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.collection = new PriorityQueue<>();
        this.initializationDate = LocalDateTime.now();
    }

    public void add(Product product) {
        collection.add(product);
        if (greatestProduct == null) {
            greatestProduct = product;
        }
        if (greatestProduct.compareTo(product) < 0) {
            greatestProduct = product;
        }
    }

    public void remove(Product product) {
        collection.remove(product);
    }

    public Product getGreatestProduct() {
        return greatestProduct;
    }

    public void clear() {
        collection.clear();
    }

    public Product getLowestProduct() {
        return collection.peek();
    }

    public Product getProductById(int id) {
        for (Product product : collection) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    // ==================== ЗАГРУЗКА / СОХРАНЕНИЕ ====================

    public void loadCollection() {
        PriorityQueue<Product> loaded = fileManager.load();
        if (loaded != null) {
            collection = loaded;
            // Обновляем счётчик id
            collection.stream()
                    .mapToInt(Product::getId)
                    .max()
                    .ifPresent(Product::updateCurrentId);
            logger.info("Collection loaded: " + collection.size() + " elements");
        } else {
            logger.warning("Failed to load collection, starting with empty collection");
        }
    }

    public void saveCollection() {
        fileManager.save(collection);
        logger.info("Collection saved: " + collection.size() + " elements");
    }



    public PriorityQueue<Product> getCollection() { return collection; }
    public FileManager getFileManager() { return fileManager; }

    public String getInitializationDate() {
        return initializationDate.toString();
    }
}
