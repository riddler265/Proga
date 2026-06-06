package interfaces;

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
import nothing.Nothing;
//nothing
import talk.Talk;

public interface Visitor {
    void visit(Prole interesting);
    void visit(OuterPartyMember interesting);
    void visit(Location interesting);
    void visit(Inventory interesting);
    void visit(Warehouse interesting);
    void visit(Item interesting);
    void visit(Ticket interesting);
    void visit(Talk interesting);
    void visit(Lottery lottery);
    void visit(Nothing interesting);
}