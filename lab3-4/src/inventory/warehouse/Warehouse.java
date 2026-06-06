package inventory.warehouse;

//exception
import exceptions.DontHaveEnoughMoneyException;
import exceptions.InventoryIsFullException;
//human
import humans.Human;
//inventory
import inventory.Inventory;
//item
import items.Item;
//location
import locations.institutions.Institution;

public class Warehouse extends Inventory {

    //fields
    protected Institution institution;

    //constructor
    public Warehouse(Human owner, Institution institution) {
        super(owner, 100);
        this.institution = institution;
    }

    //getters
    @Override
    public String getName() {
        return institution.getName() + "`s warehouse";
    }
    
    public Institution getInstitution() {
        return institution;
    }

    //item
    @Override
    public void addToInventory(Item... items) throws InventoryIsFullException{
        for (Item item : items) {
            if (this.items.size() < capacity) {
                this.items.add(item);
                System.out.println(item.getName());
                item.setOwner(owner);
            } else {
                throw new InventoryIsFullException("There is no more space in the warehouse");
            }
        }
    }

    @Override
    public void giveTo(Item item, Human person) {
        double price = item.getPrice();
        try {
            person.makePayment(price);
            try {
                person.getInventory().addToInventory(item);
                removeFromInventory(item);
                item.setOwner(person);
                owner.receivePayment(price);
                owner.say("Take it");
                institution.successSale(person, item, price);
            } catch (InventoryIsFullException e) {
                person.receivePayment(price);
                owner.say("Looks like you can`t take more.");
            } 
        } catch (DontHaveEnoughMoneyException e) {
            owner.say("No money, no honey.");
        }
    }

}