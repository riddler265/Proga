package items.sales;

//humans
import humans.Human;
//items
import items.Item;

public record Sale(Human buyer, Item item, double price) {};