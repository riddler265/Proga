package humans.party.members;

//list
import java.util.ArrayList;
import java.util.Arrays;

import builders.humans.HBuilder;
//enums
import enums.Ministries;
//human
import humans.Human;

public class InnerPartyMember extends OuterPartyMember {

    //constructor
    public InnerPartyMember(HBuilder builder) {
        super(builder);
        
        this.memories.put("Big Brother", new ArrayList<>());
        this.memories.get("Big Brother").add("Big brother is watching.");
    }

    //humans
    public void rectify(Human person) {
        person.forgetAll();
        person.addRemember("2 + 2", Arrays.asList("2 + 2 will be 5. That's what the party said."));
    }

    public void transfer(OuterPartyMember person, Ministries ministry) {
        person.transferTo(ministry);
    }
    
    //equals, hashCode, toString
    @Override
    public String toString() {
        return "Member of the inner party. Name - " + this.name + ", believes in " + this.faith + ".";
    }
}