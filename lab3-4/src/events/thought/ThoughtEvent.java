package events.thought;

//enums
import enums.Importance;
//events
import events.Event;
//humans
import humans.Human;
import humans.party.members.Lottery;
import humans.party.members.OuterPartyMember;
import humans.proles.Prole;
//inventory
import inventory.Inventory;
import inventory.warehouse.Warehouse;
//items
import items.Item;
import items.tickets.Ticket;
import locations.locations.Location;
//nothing
import nothing.Nothing;
//talk
import talk.Talk;


public class ThoughtEvent extends Event {

    //constructor
    public ThoughtEvent(Human irritated, Importance importance) {
        super(irritated, importance);
    }

    public ThoughtEvent(Human irritated) {
        super(irritated);
    }
    
    //reactions
    @Override
    protected boolean react(String interestingName, Location interestingLocation) {
        if (mustHappen()) {
            announce(irritated.getName() + " thought about " + interestingName + " and became interested.");
            return true;
        } else {
            dontReact();
            return false;
        }                    
    }
    
    @Override
    public void dontReact() {
        announce(irritated.getName() + " was thinking about something, but immediately forgot about it.");
        irritated.say("Whatever");
        satisfy.visit(Nothing.getNothing());  
    }

    //visitor
    @Override
    public void visit(Prole interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(OuterPartyMember interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Location interesting) {
        if (react(interesting.getName(), interesting)) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Inventory interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Warehouse interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Item interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Ticket interesting) {
        if (react(interesting.getName(), interesting.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Talk interesting) {
        if (react(interesting.getName(), irritated.getLocation())) {
            satisfy.visit(interesting);
        }     
    }

    @Override
    public void visit(Lottery interesting) {
        if (mustHappen()) {
            announce(irritated.getName() + " saw " + interesting.getName() + " and he became irritated.");
            satisfy.visit(interesting);
        } else {
            dontReact();
        }
    }

    @Override
    public void visit(Nothing nothing) {
        dontReact();
    }
        
}