package humans.proles;

//java
import java.util.Objects;

import builders.humans.HBuilder;
//enums
import enums.Business;
//human
import humans.Human;
//item
import items.Item;
//location
import locations.institutions.Institution;

public class Entrepreneur extends Prole {

    //fields
    protected Business business;
    protected Institution institution;

    //constructor
    public Entrepreneur(HBuilder builder) {
        super(builder);
        this.business = builder.getBusiness();
        this.institution = builder.getInstitution();
    }

    //getters
    public Business getBusiness() {
        return business;
    }

    public Institution getInstitution() {
        return institution;
    }

    //setters
    public void setBusiness(Business business) {
        this.business = business;
    }

    //institutions
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public void sale(Human buyer, Item item) {
        institution.sale(buyer, item);
    }

    public void changePrice(Item item, double price) {
        institution.changePrice(item, price);
    }

    //institutions.items
    public void onSale(Item... items) {
        institution.onSale(items);
    }

    public void outSale(Item... items) {
        institution.outSale(items);
    }

    @Override
    public void giveTo(Item item, Human person) {
        institution.sale(person, item);
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrepreneur person = (Entrepreneur) o;
        return Objects.equals(this.name, person.getName()) && Objects.equals(this.business, person.business) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.business);
    }

    @Override
    public String toString() {
        return "Name - " + this.name + ", believes in " + this.faith + ". Institution - " + this.institution.getName();
    }
}