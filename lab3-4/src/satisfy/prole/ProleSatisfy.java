package satisfy.prole;

//java
import java.util.Random;
//human
import humans.Human;
import humans.party.members.OuterPartyMember;
import humans.party.members.Lottery;
import humans.proles.Prole;
//inventory
import inventory.Inventory;
import inventory.warehouse.Warehouse;
//item
import items.Item;
import items.tickets.Ticket;
import locations.locations.Location;
//nothing
import nothing.Nothing;
//satisfy
import satisfy.Satisfy;
//talk
import talk.Talk;

public class ProleSatisfy extends Satisfy {

    //constructor
    public ProleSatisfy(Human interested) {
        super(interested);
    }

    //visitor
    @Override
    public void visit(Prole interesting) {
        location = interesting.getLocation();
        if (nearby(location)) {
            interested.talk(interesting, "Lottery");
        } else {
            find(" started looking for " + interesting.getName());
            visit(interesting);
        }
    }

    @Override
    public void visit(OuterPartyMember interesting) {
        location = interesting.getLocation();
        if (nearby(location)) {
            interested.talk(interesting, "Sky");
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
            interested.say("Is there a new lottery?");
            for (Item item : interesting.check()) {
                if (item instanceof Ticket) {
                    interested.say("Interesting!");
                    visit(item);
                    break;
                }
            }
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
                interested.addToInventory(interesting); 
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
        Prole prole = (Prole) interested;
        location = interesting.getLocation();
        if (prole.getLotteries().contains(interesting.getlottery())) {
            interested.say("I'm already participating in this lottery.");
        } else {
            if (nearby(location)) {
                if (interesting.getOwner() == null) {
                    interested.addToInventory(interesting); 
                } else {
                    interesting.getOwner().giveTo(interesting, interested);
                }
            } else {
                find(" started looking for " + interesting.getName());
                visit(interesting);
            }
        }
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
        Prole prole = (Prole) interested;
        if (prole.getLotteries().contains(interesting)) {
            interested.say("I'm already participating in this lottery.");            
        } else {
            Random random = new Random();
            int id = random.nextInt(interesting.getTicketTotal()) + 1;
            interested.say("I really need a ticket " + id + ".");
            visit(interesting.getTicket(id));
        }
    }

    @Override
    public void visit(Nothing interesting) {
        announce(interested.getName() + " have nothing to be interested in.");    
    }
}