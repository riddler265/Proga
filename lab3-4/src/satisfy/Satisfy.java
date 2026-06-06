package satisfy;

//java
import java.util.Objects;
//human
import humans.Human;
//interface
import interfaces.Visitor;
import locations.locations.Location;

public abstract class Satisfy implements Visitor {

    //fields
    protected Human interested;
    protected Location location;

    //constructor
    public Satisfy(Human interested) {
        this.interested = interested;
    }

    //conditions
    protected boolean nearby(Location location) {
        return Objects.equals(interested.getLocation(), location);
    }

    //message
    protected void announce(String message) {
        System.out.println(message);
    }

    //find
    protected void find(String message) {
        announce(interested.getName() + message);
        interested.moveTo(location);
    }
}