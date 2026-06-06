package humans.party.members;

//java
import java.util.ArrayList;
import java.util.Objects;

import builders.humans.HBuilder;
//enums
import enums.Ministries;
//human
import humans.Human;
//satisfy
import satisfy.Satisfy;
import satisfy.party.PartySatisfy;
import satisfy.traitor.TraitorSatisfy;

public class OuterPartyMember extends Human {

    //fields
    protected Ministries ministry;
    protected boolean traitor;

    //constructor
    public OuterPartyMember(HBuilder builder) {
        super(builder);
        this.ministry = builder.getMinistry();
        this.traitor = builder.getTraitor();

        this.memories.put("Party", new ArrayList<>());
        this.memories.get("Party").add("2 + 2 will be 5. That's what the party said.");
    }

    //getters
    public Ministries getMinistry() {
        return ministry;
    }

    public boolean isTraitor() {
        return traitor;
    }

    //party
    protected void transferTo(Ministries ministry) {
        this.ministry = ministry;
    }

    public Lottery makeLottery(String name, double prize, int tickets) {
        if (this.ministry == Ministries.PLENTY) {
            return new Lottery(name, prize, tickets);
        } else {
            return null;
        }
    }

    //interest
    @Override
    public Satisfy satisfy() {
        switch (faith) {
            case PARTY:
                return new PartySatisfy(this);
            case PROLES:
                return new TraitorSatisfy(this);
            default:
                return new PartySatisfy(this);
        }
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OuterPartyMember person = (OuterPartyMember) o;
        return Objects.equals(this.name, person.name) && Objects.equals(this.ministry, person.ministry) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.ministry);
    }

    @Override
    public String toString() {
        return "Member of the outer party. Name - " + this.name + ", believes in " + this.faith + ". Trator - " + this.traitor;
    }

}

    