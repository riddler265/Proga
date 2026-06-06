package locations.locations;

import java.util.ArrayList;
import java.util.List;
//java
import java.util.Objects;

//enums
import enums.Purity;
import humans.Human;
import locations.Country;

public class Location extends Country{
    
    //fields
    protected List<Human> people = new ArrayList<>();
    protected Purity purity;
    protected Country parentLocation;
    
    //constructor
    public Location(String name, String description, Purity purity, Country parentLocation) {
        super(name, description);
        this.purity = purity;
        this.parentLocation = parentLocation;
    }

    //getters
    public List<Human> getPeople() {
        return people;
    }

    public Purity getPurity() {
        return purity;
    }

    public Country getParentLocation() {
        return parentLocation;
    }

    //setters
    public void setParentLocation(Location location) {
        this.parentLocation = location;
    }

    //human
    public void addHuman(Human... persons) {
        for (Human person : persons) {
            if (!people.contains(person)) {
                people.add(person);
            }
        }
    }

    public void removeHuman(Human... persons) {
        for (Human person : persons) {
            people.remove(person);
        }
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(this.description, location.description) && Objects.equals(this.parentLocation, location.parentLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.parentLocation);
    }

    @Override
    public String toString() {
        return this.name + this.description;
    }
}