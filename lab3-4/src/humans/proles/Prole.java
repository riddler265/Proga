package humans.proles;

//java
import java.util.Objects;

import builders.humans.HBuilder;

import java.util.ArrayList;
import java.util.List;
//enums
import enums.Hope;
//humans
import humans.Human;
import humans.party.members.Lottery;
//satisfy
import satisfy.Satisfy;
import satisfy.prole.ProleSatisfy;

public class Prole extends Human {

    //fields
    protected boolean lotteryAddiction;
    protected List<Lottery> lotteries;
    protected Hope hope;

    //constructor
    public Prole(HBuilder builder) {
        super(builder);
        this.lotteryAddiction = builder.getLotteryAddiction();
        this.hope = builder.getHope();

        this.memories.put("Lottery", new ArrayList<>());
        this.memories.get("Lottery").add("Someday I'll win.");
        this.lotteries = new ArrayList<>();
        }

    //getters
    public boolean getLotteryAddiction() {
        return lotteryAddiction;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public Hope getLotteryHope() {
        return hope;
    }

    //lottery
    public void addLottery(Lottery lottery) {
        if (!lotteries.contains(lottery)) {
            this.lotteries.add(lottery);
            addRemember("Lottery", List.of("I'm playing the lottery " + lottery.getName() + "."));
        }
    }

    //interest
    @Override
    public Satisfy satisfy() {
        return new ProleSatisfy(this);
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prole person = (Prole) o;
        return Objects.equals(this.name, person.name) && Objects.equals(this.age, person.age) ;
    }

    @Override
    public String toString() {
        return "Prol. Name - " + this.name + ", believes in " + this.faith;
    }

}
