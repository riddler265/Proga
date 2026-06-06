package exceptions;

public class InventoryIsFullException extends Exception {
    public InventoryIsFullException() {
        super("Inventory is full.");
    }
    public InventoryIsFullException(String message) {
        super(message);
    }
}