package collectionManager;

import java.util.PriorityQueue;

import product.Product;
import java.time.LocalDateTime;

public class CollectionManager {

    //fields
    private final PriorityQueue<Product> collection = new PriorityQueue<>();
    private final LocalDateTime creationTime;
    private Product greatestProduct = null;

    //constructor
    public CollectionManager() {
        this.creationTime = LocalDateTime.now();
    }

    //getters
    public String getInfo() {
        return "\nТип коллекции: " + collection.getClass().getSimpleName() + "\n" +
                "Время создания: " + creationTime.toString() + "\n" +
                "Тип элемента: Product\n" +
                "Размер коллекции: " + collection.size() + "\n";
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

    //add
    public void addToCollection(Product product) {
        collection.add(product);
        System.out.println("В коллекцию добавлен новый предмет: \n" + product.toString() + "\n");
        if (greatestProduct == null) {
            greatestProduct = product;
        }
        if (greatestProduct.compareTo(product) < 0) {
            greatestProduct = product;
        }
    }

    public void removeFromCollection(Product product) {
        collection.remove(product);
        System.out.println("Из коллекции удален предмет:\n" + product + "\n");
    }

}
