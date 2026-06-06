package humans;

//java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
//enums
import enums.FacialHair;
import enums.Faith;
import enums.Posture;
//exceptions
import exceptions.DontHaveEnoughMoneyException;
import exceptions.InventoryIsFullException;
//inventory
import inventory.Inventory;
//item
import items.Item;
import locations.locations.Location;
//talk
import talk.Talk;
//satisfy
import satisfy.Satisfy;
//builder
import builders.humans.HBuilder;

public abstract class Human {

    //fields
    protected String name;
    protected int age;
    protected boolean rectified;
    protected Map<String, List<String>> memories = new HashMap<>();
    protected Faith faith;
    protected double money;
    protected Inventory inventory;
    protected FacialHair facialHair;
    protected Posture posture;
    protected Location location;

    //constructor
    public Human(HBuilder builder) {
        this.name = builder.getName();
        this.age = builder.getAge();
        this.rectified = builder.getRectified();
        this.faith = builder.getFaith();
        this.money = builder.getMoney();
        this.facialHair = builder.getFacialHair();
        this.posture = builder.getPosture();
        this.location = builder.getLocation();

        this.memories.put("Sky", new ArrayList<>());
        this.memories.get("Sky").add("I remember how beautiful the sky was that day.");

        this.inventory = new Inventory(this, 6);
    }

    //getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Faith getFaith() {
        return faith;
    }

    public boolean isRectified() {
        return rectified;
    }

    public Location getLocation() {
        return location;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<String, List<String>> getMemories() {
        return memories;
    }

    //setters
    public void setFaith(Faith faith) {
        this.faith = faith;
        say("Hope in the " + faith + ".");
    }

    //inventory
    public void addToInventory(Item... items) {
        try {
            inventory.addToInventory(items);
            System.out.println(name + " took the Items.\n");
        } catch (InventoryIsFullException e) {
            say("I can`t take more.\n");
        }
    }

    public void removeFromInventory(Item... items) {
        this.inventory.removeFromInventory(items);
        System.out.println(this.name + " doesn't own these things anymore.\n");
    }

    public void check() {
        this.say("I have:");
        for (Item item : this.inventory.check()) {
            System.out.println("    " + item.getName());
        }
    }

    public void giveTo(Item item, Human person) {
        this.inventory.giveTo(item, person);
    }

    //location
    public void moveTo(Location location) {

        //moveFrom
        if (this.location != null) {
            this.location.removeHuman(this);            
        }

        //moveTo
        this.location = location;
        this.location.addHuman(this);
        System.out.println(name + " arrived to " + location.getName());

        //isFamiliar
        if (memories.containsKey(location.getName())) {
            say("Looks familiar \n");
        } else {
            addRemember(location.getName(), List.of("I came here once"));
            System.out.println();
        }

    }

    //interest
    public abstract Satisfy satisfy();

    //money
    public void makePayment(double amount) throws DontHaveEnoughMoneyException {
        if (this.money >= amount) {
            this.money -= amount;
        } else {
            throw new DontHaveEnoughMoneyException(this);
        }
    }

    public void receivePayment(double amount) {
        this.money += amount;
    }

    //communicate
    public void say(String think) {
        System.out.println(this.name + ": " + think);
    }

    public void talk(Human listener, String theme) {
        Talk talk = new Talk(this, listener, theme);
        talk.happen();
    }

    public List<String> remember(String think) {

        if (think == null || think.isEmpty()) {
            this.say("Remember about what?");
            return Collections.emptyList();
        }

        if (memories.containsKey(think)) {
            this.say(think + " ?");
            for (String memory : memories.get(think)) {
                this.say(memory);
            }
            return memories.get(think);
        }

        this.say(think + " ? Don`t remember about that.");
        System.out.println();
        return Collections.emptyList();
    }

    public void addRemember(String title, List<String> memories) {

        if (memories == null || memories.isEmpty()) {
            return;
        }

        if (this.memories.containsKey(title)) {
            this.memories.get(title).addAll(memories);
            return;
        }

        this.memories.put(title, new ArrayList<>(memories));
        return; 
    }

    public void forgetAll() {
        this.memories.clear();
        this.say("Who am I? I remember nothing... \n");
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human person = (Human) o;
        return Objects.equals(this.name, person.name) && this.age == person.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.age);
    }

    @Override
    public String toString() {
        return "Name - " + this.name + ", believes in " + this.faith + ".";
    }
}