package satisfy.party;

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

public class PartySatisfy extends Satisfy {

    //constructor
    public PartySatisfy(Human interested) {
        super(interested);
    }

    //visitor
    @Override
    public void visit(Prole interesting) {
        interested.say("A good party member shouldn't have to talk to these idiots.");
    }

    @Override
    public void visit(OuterPartyMember interesting) {
        location = interesting.getLocation();
        if (nearby(location)) {
            interested.talk(interesting, "Party");
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
            find(": I'll take a walk to" + interesting.getName());
            visit(interesting);
        }
    }

    @Override
    public void visit(Inventory interesting) {
        location = interesting.getOwner().getLocation();
        if (nearby(location)) {
            interested.say(interesting.getOwner().getName() + ", can you show me what you have?");
            interesting.getOwner().check();
        } else {
            find(" started looking for " + interesting.getOwner().getName());
            visit(interesting);    
        }
    }

    @Override
    public void visit(Warehouse interesting) {
        location = interesting.getInstitution();
        if (nearby(location)) {
            interesting.getInstitution().assortment();
        } else {
            find(" started looking for " + interesting.getOwner().getName());
            visit(interesting);    
        }
    }

    @Override
    public void visit(Item interesting) {
        location = interesting.getLocation();
        if (nearby(location)) {
            if (interesting.getOwner() == null) {
                    interested.say("I shouldn't take this.");
                } else {
                    interesting.getOwner().giveTo(interesting, interested);
                }
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
        interested.say("It's not worth eavesdropping on.");
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