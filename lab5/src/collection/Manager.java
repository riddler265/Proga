package collection;

import java.util.PriorityQueue;

import exceptions.IncorrectInputException;
import product.Product;
import java.time.LocalDateTime;

public class Manager {

    //fields
    private final PriorityQueue<Product> collection = new PriorityQueue<>();
    private final LocalDateTime creationTime;
    private Product greatestProduct = null;

    //constructor
    public Manager() {
        this.creationTime = LocalDateTime.now();
    }

    //getters
    public String getInfo() {
        String info = "\nType: " + collection.getClass().toString().replaceAll(".*\\.", "") + "\n" +
                "Creation time: " + creationTime.toString() + "\n" +
                "Element type: Product\n" +
                "Collection size: " + collection.size() + "\n";
        return info;
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
        System.out.println("\nВ коллекцию добавлен новый предмет: \n" + product.toString());
        if (greatestProduct == null) {
            greatestProduct = product;
        }
        if (greatestProduct.compareTo(product) < 0) {
            greatestProduct = product;
        }
    }

    public void removeFromCollectiob(Product product) {
        collection.remove(product);
    }

}
