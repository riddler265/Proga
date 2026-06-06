package locations;

public class Country {

    //fields
    protected final String name;
    protected final String description;

    //constructor
    public Country(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //equals, hashCode, toString
    @Override
    public String toString() {
        return this.name + this.description;
    }
}
