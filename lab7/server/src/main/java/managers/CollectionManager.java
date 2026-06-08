package managers;

import db.DatabaseManager;
import model.Person;
import model.Product;
import subscription.NotificationBus;
import subscription.Subscription;
import subscription.SubscriptionChecker;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

/**
 * Менеджер коллекции.
 * Хранит PriorityBlockingQueue<Product> — потокобезопасный аналог PriorityQueue.
 * Все модификации проходят через БД: сначала INSERT/UPDATE/DELETE в БД,
 * только при успехе — обновляем коллекцию в памяти.
 */
public class CollectionManager {

    private static final Logger logger = Logger.getLogger(CollectionManager.class.getName());

    // Потокобезопасная коллекция (требование задания)
    private final PriorityBlockingQueue<Product> collection = new PriorityBlockingQueue<>();
    private final LocalDateTime initializationDate;
    private final DatabaseManager db;

    private final LinkedList<String> history = new LinkedList<>();

    public CollectionManager(DatabaseManager db) {
        this.db = db;
        this.initializationDate = LocalDateTime.now();
    }

    // =====================================================================
    // LOAD
    // =====================================================================

    public void loadCollection() {
        try {
            collection.clear();
            collection.addAll(db.loadAll());
            logger.info("Collection loaded from DB: " + collection.size() + " elements");
        } catch (SQLException e) {
            logger.severe("Failed to load collection from DB: " + e.getMessage());
        }
    }

    // =====================================================================
    // ADD — сначала БД, потом память
    // =====================================================================

    /**
     * Добавляет продукт: INSERT в БД → при успехе добавляем в память.
     * @return true если добавлено успешно
     */
    public boolean add(Product product, String ownerLogin) {
        try {
            db.insertProduct(product, ownerLogin); // устанавливает product.id и creationDate
            collection.add(product);
            checkSubscriptions(product);
            logger.info("Product added: id=" + product.getId());
            return true;
        } catch (SQLException e) {
            logger.severe("Failed to add product to DB: " + e.getMessage());
            return false;
        }
    }

    // =====================================================================
    // UPDATE
    // =====================================================================

    public boolean update(Product product, String requestLogin) {
        if (!product.getOwnerLogin().equals(requestLogin)) return false;
        try {
            boolean ok = db.updateProduct(product);
            if (ok) {
                // Заменяем в памяти
                collection.removeIf(p -> p.getId() == product.getId());
                collection.add(product);
                checkSubscriptions(product);
            }
            return ok;
        } catch (SQLException e) {
            logger.severe("Failed to update product: " + e.getMessage());
            return false;
        }
    }

    // =====================================================================
    // REMOVE
    // =====================================================================

    /** Удаляет по id. Только владелец может удалить. */
    public boolean removeById(int id, String requestLogin) {
        Product product = getProductById(id);
        if (product == null) return false;
        if (!product.getOwnerLogin().equals(requestLogin)) return false;
        try {
            boolean ok = db.deleteProduct(id);
            if (ok) collection.removeIf(p -> p.getId() == id);
            return ok;
        } catch (SQLException e) {
            logger.severe("Failed to delete product: " + e.getMessage());
            return false;
        }
    }

    /** Удаляет все продукты пользователя. */
    public int clearByOwner(String ownerLogin) {
        try {
            int removed = db.deleteProductsByOwner(ownerLogin);
            collection.removeIf(p -> ownerLogin.equals(p.getOwnerLogin()));
            return removed;
        } catch (SQLException e) {
            logger.severe("Failed to clear collection: " + e.getMessage());
            return 0;
        }
    }

    /** Удаляет все продукты (admin-операция или команда clear для всей коллекции по заданию). */
    public int clearAll() {
        try {
            int removed = db.deleteAllProducts();
            collection.clear();
            return removed;
        } catch (SQLException e) {
            logger.severe("Failed to clear all: " + e.getMessage());
            return 0;
        }
    }

    // =====================================================================
    // QUERIES
    // =====================================================================

    public Product getProductById(int id) {
        for (Product p : collection) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Product getLowestProduct() {
        return collection.peek();
    }

    public PriorityBlockingQueue<Product> getCollection() {
        return collection;
    }

    public String getInitializationDate() {
        return initializationDate.format(Person.formatter);
    }

    public DatabaseManager getDb() { return db; }

    // =====================================================================
    // SUBSCRIPTIONS
    // =====================================================================

    private void checkSubscriptions(Product product) {
        try {
            List<Subscription> subs = db.getAllSubscriptions();
            logger.info("Checking " + subs.size() + " subscriptions for product id=" + product.getId());

            List<SubscriptionChecker.Match> matches = SubscriptionChecker.check(subs, product);

            logger.info("Subscriptions matched: " + matches.size());

            for (SubscriptionChecker.Match m : matches) {
                Subscription sub = m.subscription;
                // Сообщение о конкретном сработавшем условии
                String msg = "Product '" + product.getName() + "' (id=" + product.getId() + ")"
                    + " matched your subscription #" + sub.getId() + ": "
                    + sub.getField() + " " + sub.getOperator() + " " + sub.getThreshold()
                    + " (actual " + sub.getField() + " = " + m.actualValue + ")";
                NotificationBus.getInstance().notify(sub.getUserLogin(), msg);
                logger.info("Notified " + sub.getUserLogin() + " about subscription #" + sub.getId());
            }
        } catch (SQLException e) {
            logger.warning("Failed to check subscriptions: " + e.getMessage());
        }
    }
}
