package inventory;

//java
import java.util.ArrayList;
import java.util.List;
//exception
import exceptions.InventoryIsFullException;
//human
import humans.Human;
//item
import items.Item;

public class Inventory {

    //fields
    protected Human owner;
    protected List<Item> items = new ArrayList<>();
    protected final int capacity;

    //constructor
    public Inventory(Human owner, int capacity) {
        this.owner = owner;
        this.capacity = capacity;
    }

    //getters
    public String getName() {
        return owner.getName() + "`s inventory";
    }

    public Human getOwner() {
        return owner;
    }

    //add, remove, check
    public void addToInventory(Item... items) throws InventoryIsFullException {
        for (Item item : items) {
            if (this.items.size() < capacity) {
                this.items.add(item);
                System.out.println(item.getName());
                item.setOwner(owner);
            } else {
                throw new InventoryIsFullException("There is no space in the " + owner.getName() + "`s inventory.");
            }
        }
    }

    public void removeFromInventory(Item... items) {
        for (Item item : items) {
            this.items.remove(item);
            System.out.println(item.getName());
            item.removeOwner();
        }
    }

    public List<Item> check() {
        return items;
    }

    public void giveTo(Item item, Human person) {
        try {
            person.getInventory().addToInventory(item);
            owner.removeFromInventory(item);
            item.setOwner(person);
            owner.say("Take it");
        } catch (InventoryIsFullException e) {
            owner.say(" Looks like you can`t take more.");
        }
    }
}