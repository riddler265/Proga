package managers;

import json.ServerResponse;
import model.Person;
import model.Product;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

/**
 * Класс, управляющий коллекцией {@link Product}.
 */
public class CollectionManager {

    //fields
    private final PriorityQueue<Product> collection = new PriorityQueue<>();
    private final LocalDateTime creationTime;
    private Product greatestProduct = null;


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
    public ServerResponse getInfo() {
        return new ServerResponse("collection.info", collection.getClass().getSimpleName(),
                creationTime.format(Person.formatter),Integer.toString(collection.size()));
    }

    public Product getProductById(int id) {
        return collection.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
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
    public ServerResponse addToCollection(Product product) {
        collection.add(product);
        if (greatestProduct == null || greatestProduct.compareTo(product) < 0) {
            greatestProduct = product;
        }
        return new ServerResponse("add.succes", product.toString());
    }

    public ServerResponse removeFromCollection(Product product) {
        collection.remove(product);
        return new ServerResponse("remove.success", Integer.toString(product.getId()), product.getName());
    }

}
