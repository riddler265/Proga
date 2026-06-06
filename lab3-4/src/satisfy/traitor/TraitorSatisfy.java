package satisfy.traitor;

import humans.Human;
import humans.party.members.OuterPartyMember;
import humans.party.members.Lottery;
import humans.proles.Prole;
import inventory.Inventory;
import inventory.warehouse.Warehouse;
import items.Item;
import items.tickets.Ticket;
import locations.locations.Location;
import nothing.Nothing;
import satisfy.Satisfy;
import talk.Talk;

public class TraitorSatisfy extends Satisfy {

    //constructor
    public TraitorSatisfy(Human interested) {
        super(interested);
    }

    //conditions
    protected boolean isSafe(Location location) {
        return location.getPeople().size() < 3 || location.getPeople().size() > 10;
    }

    //visitor
    @Override
    public void visit(Prole interesting) {
        location = interesting.getLocation();
        if (nearby(location) && isSafe(location)) {
            interested.talk(interesting, "Old life");
        } else if (nearby(location)) {
            interested.say("I can't attach attention to myself right now");
        } else {
            find(" started looking for " + interesting.getName());
            visit(interesting);
        }        
    }

    @Override
    public void visit(OuterPartyMember interesting) {
        location = interesting.getLocation();
        if (nearby(location) && isSafe(location)) {
            interested.talk(interesting, "Brotherhood");
        } else if (nearby(location)) {
            interested.say("I can't attach attention to myself right now");
        } else {
            find(" started looking for " + interesting.getName());
            visit(interesting);
        }        
    }

    @Override
    public void visit(Location interesting) {
        location = interesting;
        if (nearby(interesting)) {
            interested.say("I'm already here.");
        } else {
            find(": I need to arrive without arousing suspicion.");
            visit(interesting);
        }
    }

    @Override
    public void visit(Inventory interesting) {
        location = interesting.getOwner().getLocation();
        if (nearby(location) && isSafe(location)) {
            interested.say(interesting.getOwner().getName() + ", can you show me what you have?");
            interesting.getOwner().check();
        } else if (nearby(location)) {
            interested.say("It's dangerous to be interested in " + interesting.getName());
        } else {
            find(" started looking for " + interesting.getOwner().getName());
            visit(interesting);    
        }
    }

    @Override
    public void visit(Warehouse interesting) {
        location = interesting.getInstitution();
        if (nearby(location) && isSafe(location)) {
            interesting.getInstitution().assortment();
        } else if (nearby(location)) {
            interested.say("It's dangerous to be interested in " + interesting.getName());
        } else {
            find(" started looking for " + interesting.getOwner().getName());
            visit(interesting);    
        }
    }

    @Override
    public void visit(Item interesting) {
        location = interesting.getLocation();
        if (nearby(location) && isSafe(location)) {
            if (interesting.getOwner() == null) {
                    interested.addToInventory(interesting);
                } else {
                    interesting.getOwner().giveTo(interesting, interested);
                }
        } else if (nearby(location)) {
            interested.say("It's not safe to take this");    
        } else {
            find(" started looking for " + interesting.getName());
            visit(interesting);
        }
    }

    @Override
    public void visit(Ticket interesting) {
        announce(interested.getName() + " knows that there's no point in playing the lottery.");
    }

    @Override
    public void visit(Talk interesting) {
        location = interesting.getLocation();
        if (nearby(location)) {
            announce(interested.getName() + " eavesdropping " + interesting.getName());
            interested.addRemember(interesting.getTheme(), interesting.getTalk());
        } else {
            find(": I need to get closer to hear");
            visit(interesting);
        }
    }

    @Override
    public void visit(Lottery interesting) {
        announce(interested.getName() + " knows that there's no point in playing the lottery.");
    }

    @Override
    public void visit(Nothing interesting) {
        announce(interested.getName() + " have nothing to be interested in.");    
    }
}