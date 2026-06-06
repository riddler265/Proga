package locations.institutions;

import java.util.ArrayList;
import java.util.List;

import builders.humans.HBuilder;
//enums
import enums.Purity;
//exception
import exceptions.InventoryIsFullException;
//humans
import humans.Human;
import humans.proles.Entrepreneur;
//interfaces
import interfaces.Owner;
//inventory
import inventory.warehouse.Warehouse;
//item
import items.Item;
import items.sales.Sale;
import locations.locations.Location;

public class Institution extends Location implements Owner{

    //fields
    protected Human owner;
    protected Warehouse products;
    protected List<Sale> salesJournal = new ArrayList<>();

    //constructor
    public Institution(String description, Purity purity, Location parentLocation, Human owner) {
        super(owner.getName() + "`s institution", description, purity, parentLocation);
        setOwner(owner);
        this.products = new Warehouse(owner, this);
    }

    //constructor without owner
    public Institution(String description, Purity purity, Location parentLocation) {
        this(description, purity, parentLocation, new HBuilder().buildEntrepreneur());
    }

    //getters
    public Entrepreneur getOwner() {
        return (Entrepreneur) owner;
    }

    public List<Item> getItems() {
        return products.check();
    }

    //prices
    public double getPrice(Item item) {
        return item.getPrice();
    }

    public void changePrice(Item item, double price) {
        item.setPrice(price);
    }

    //items
    public void onSale(Item... items) {
        try {
            products.addToInventory(items);
            System.out.println("Now these items are sold in " + this.name);
        } catch (InventoryIsFullException e) {
            System.out.println("The warehouse is full");
        }
    }

    public void outSale(Item... items) {
        products.removeFromInventory(items);
        System.out.println("Now these items are not sold in " + this.name + "\n");
    }

    public void assortment() {
        System.out.println("In the warehouse of the " + this.name);
        for (Item item : this.products.check()) {
            System.out.println("    " + item.getName());
        }
    }

    public void sale(Human buyer, Item item) {
        products.giveTo(item, buyer);
    }

    public void successSale(Human buyer, Item item, double price) {
        this.salesJournal.add(new Sale(buyer, item, price));
    }

    //human
    @Override
    public void setOwner(Human human) {
        if (human instanceof Entrepreneur) {
            Entrepreneur person = (Entrepreneur) human;
            person.setInstitution(this);
            this.owner = human;
        } else {
            System.out.println(human.getName() + " can't own an institution.");
        }
    }

    @Override
    public void removeOwner() {
        this.owner = null;
    }


}