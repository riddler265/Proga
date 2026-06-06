package items;

//java
import java.util.Objects;
//enums
import enums.Conditions;
//human
import humans.Human;
//interface
import interfaces.Owner;
import locations.locations.Location;

public class Item implements Owner {

    //fields
    protected String name;
    protected String description;
    protected double price;
    protected Human owner;
    protected Conditions condition;
    protected Location location;

    //constructor
    public Item(String name, String description, double price, Human owner, Conditions condition) {
        this.name = name;
        this.description = condition + " " + description;
        this.price = price;
        this.owner = owner;
        this.condition = condition;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Human getOwner() {
        return owner;
    }

    public Conditions getCondition() {
        return condition;
    }

    public Location getLocation() {
        if (owner != null) {
            return owner.getLocation();
        } else {
            return location;
        }
    }

    //price
    public void setPrice(double price) {
        this.price = price;
    }

    //human
    @Override
    public void setOwner(Human person) {
        this.owner = person;
    }

    @Override
    public void removeOwner() {
        this.location = owner.getLocation();
        this.owner = null;
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(this.name, item.name) && Objects.equals(this.condition, item.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.condition);
    }

    @Override
    public String toString() {
        return this.name + ". " + this.description + ". Price: " + this.price;
    }
}