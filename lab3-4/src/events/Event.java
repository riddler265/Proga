package events;

//java
import java.util.Objects;
//enums
import enums.Importance;
//humans
import humans.Human;
//interface
import interfaces.Visitor;
import locations.locations.Location;
//satisfy
import satisfy.Satisfy;

public abstract class Event implements Visitor {
    
    //fields
    protected Human irritated;
    protected double importance;
    protected Satisfy satisfy;

    //constructors
    public Event(Human irritated, Importance importance) {
        this.irritated = irritated;
        this.importance = importance.getImportance();
        this.satisfy = irritated.satisfy();
    }

    public Event(Human irritated) {
        this(irritated, Importance.COMPELLING);
    }

    //conditions
    protected double getValue() {
        return Math.random();
    }

    protected boolean nearby(Location location) {
        return Objects.equals(irritated.getLocation(), location);
    }

    protected boolean mustHappen() {
        return importance >= getValue();
    }

    //message
    protected void announce(String message) {
        System.out.println(message);
    }

    //reactions
    protected abstract boolean react(String interestingName, Location interestingLocation);

    protected abstract void dontReact();

}