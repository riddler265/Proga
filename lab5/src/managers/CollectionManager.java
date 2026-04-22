package managers;

import java.util.PriorityQueue;

import model.Person;
import model.Product;
import java.time.LocalDateTime;

/**
 * Класс, управляющий коллекцией {@link Product}.
 */
public class CollectionManager {

    //fields
    private final PriorityQueue<Product> collection = new PriorityQueue<>();
    private final LocalDateTime creationTime;
    private Product greatestProduct = null;
    private final AnnounceManager announceManager = AnnounceManager.getInstance();

    /**
     * Конструктор.
     */
    public CollectionManager() {
        this.creationTime = LocalDateTime.now();
    }

    /**
     * Метод, возвращающий информацию о коллекции.
     * @return информация о коллекции.
     */
    public String getInfo() {
        return announceManager.cTCL("collection.info", collection.getClass().getSimpleName(),
                creationTime.format(Person.formatter), Integer.toString(collection.size()));
    }

    public Product getProductById(int id) {
        for (Product product : collection) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    public PriorityQueue<Product> getCollection() {
        return collection;
    }

    //estProducts
    public Product getGreatestProduct() {
        return greatestProduct;
    }

    public Product getLowestProduct() {
        return collection.peek();
    }

    /**
     * Добавление {@link Product} в коллекцию.
     * <p>
     * @param product - Продукт. Становится
     * наибольшим, если коллекция пуста,
     * сравнивается с наибольшим, если это
     * не так.
     */
    public void addToCollection(Product product) {
        collection.add(product);
        announceManager.println("add.success", product.toString());
        if (greatestProduct == null) {
            greatestProduct = product;
        }
        if (greatestProduct.compareTo(product) < 0) {
            greatestProduct = product;
        }
    }

    public void removeFromCollection(Product product) {
        collection.remove(product);
        announceManager.println("remove.success", Integer.toString(product.getId()), product.getName());
    }

}
