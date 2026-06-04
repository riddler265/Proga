package network;

import model.Product;

import java.util.List;

/**
 * Ответ сервера клиенту.
 * Передаётся в сериализованном виде (Java Serialization).
 */
public class Response {

    private boolean success;
    private String message;
    private List<Product> products; // используется для show

    public Response() {}

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, List<Product> products) {
        this.success = success;
        this.message = message;
        this.products = products;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<Product> getProducts() { return products; }
}
