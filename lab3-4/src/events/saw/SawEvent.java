package events.saw;

import java.util.List;

import enums.Faith;
//enums
import enums.Importance;
//events
import events.Event;
//humans
import humans.Human;
import humans.party.members.OuterPartyMember;
import humans.party.members.Lottery;
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


public class SawEvent extends Event {

    //constructor
    public SawEvent(Human irritated, Importance importance) {
        super(irritated, importance);
    }

    public SawEvent(Human irritated) {
        super(irritated);
    }
    
    //reactions
    @Override
    protected boolean react(String interestingName, Location interestingLocation) {
        if (mustHappen() && nearby(interestingLocation)) {
            announce(irritated.getName() + " saw " + interestingName + " and became interested.");
            return true;
        } else if (mustHappen()) {
            announce(irritated.getName() + " is too far away to see " + interestingName);
            return false;
        } else {
            dontReact();
            return false;
        }                    
    }
    
    @Override
    public void dontReact() {
        announce(irritated.getName() + " saw something, but didn't pay attention to it.");
        irritated.say("Whatever");
        satisfy.visit(Nothing.getNothing());  
    }

    //visitor
    @Override
    public void visit(Prole interesting) {
        if (react(interesting.getName(), interesting.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    public void visit(List<Human> people) {
        if (irritated instanceof OuterPartyMember) {
            OuterPartyMember member = (OuterPartyMember) irritated;
            if (member.isTraitor()) {
                member.setFaith(Faith.PROLES);
                return;
            }
        }
        irritated.say("Proles");
    }

    @Override
    public void visit(OuterPartyMember interesting) {
        if (react(interesting.getName(), interesting.getLocation())) {
            satisfy.visit(interesting);
        }
    }

    @Override
    public void visit(Location location) {
        dontReact();
    }

    @Override
    public void visit(Inventory interesting) {
        dontReact();
    }

    @Override
    public void visit(Warehouse interesting) {
        dontReact();
    }

    @Override
    public void visit(Item interesting) {
        if (react(interesting.getName(), interesting.getLocation())) {
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
        if (react(interesting.getName(), interesting.getLocation())) {
            satisfy.visit(interesting);
        }     
    }

    @Override
    public void visit(Lottery interesting) {
        if (mustHappen()) {
            announce(irritated.getName() + " saw " + interesting.getName() + " and he became interested.");
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