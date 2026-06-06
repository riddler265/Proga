package talk;

//java
import java.util.ArrayList;
import java.util.List;
//human
import humans.Human;
import locations.locations.Location;

public class Talk {

    //fields
    protected String name;
    protected Human instigator;
    protected Human listener;
    protected Location location;
    protected String theme;
    protected List<String> talk;
    
    //constructor
    public Talk(Human instigator, Human listener, String theme) {    
        this.name = instigator.getName() + " and " + listener.getName() + " talk";
        this.instigator = instigator;
        this.listener = listener;
        this.location = instigator.getLocation();
        this.theme = theme;
        this.talk = new ArrayList<>();
    }

    public Talk(Human instigator, Human listener) {
        this(instigator, listener, "Sky");
    }

    //getters
    public String getName() {
        return name;
    }

    public Human getInstigator() {
        return instigator;
    }

    public Human getListener() {
        return listener;
    }

    public Location getLocation() {
        return location;
    }

    public String getTheme() {
        if (theme == null) {
            return "Talking about nothing";
        } else {
            return theme;
        }
    }

    public List<String> getTalk() {
        return talk;
    }

    //happen
    public void happen() {
        System.out.println(getName() + " just started\n");
        if (this.theme == null || this.theme.trim().isEmpty()) {
            this.theme = "Sky";
        }

        List<String> instigatorThoughts = instigator.remember(this.theme);
        List<String> listenerThoughts = listener.remember(this.theme);

        for (String thought : instigatorThoughts) {
            this.talk.add(instigator.getName() + ": " + thought);
        }

        for (String thought : listenerThoughts) {
            this.talk.add(listener.getName() + ": " + thought);
        }

        if (!instigatorThoughts.isEmpty()) {
            listener.addRemember(this.theme, instigatorThoughts);
        }
        
        if (!listenerThoughts.isEmpty()) {
            instigator.addRemember(this.theme, listenerThoughts);
        }
        System.out.println();
    }
}