import builders.humans.HBuilder;
import enums.Business;
import enums.Conditions;
import enums.FacialHair;
import enums.Faith;
import enums.Ministries;
import enums.Posture;
import enums.Purity;
import events.thought.ThoughtEvent;
import humans.party.members.Lottery;
import humans.party.members.OuterPartyMember;
import humans.proles.Entrepreneur;
import humans.proles.Prole;
import items.Item;
import locations.Country;
import locations.institutions.Institution;
import locations.locations.Location;
import locations.structures.LocationStructure;
import talk.Talk;

import java.util.List;
import events.Event;
import events.heard.HeardEvent;
import events.saw.SawEvent;


public class Main {
    public static void main(String[] args) {

        //WORLD



        //locations
        Country oceania = new Country("Oceania", "totalitarian superstate governed by The Party under the singular leadership of Big Brother. The state operates on the principles of Ingsoc, exercising absolute control over the population through systematic surveillance, the elimination of historical records, and the enforcement of the slogans: \"War is Peace, Freedom is Slavery, Ignorance is Strength.\"");
        LocationStructure hill = new LocationStructure("Hill", "The hill down which the street runs", Purity.CLEAR, oceania);
        LocationStructure street = new LocationStructure("Street", "A street descending from a hill", Purity.CLEAR, hill);
        hill.addLocation(street);
        Location ladder = new Location("Ladder", "Ladder at the end of the street", Purity.CLEAR, street);
        street.addLocation(ladder);
        LocationStructure artery = new LocationStructure("The artery of the district", "The main artery of the district", Purity.DIRTY, oceania);
        LocationStructure mainStreet = new LocationStructure("Main street", "Main street", Purity.CLEAR, artery);
        LocationStructure alley = new LocationStructure("Alley", "An alley where several merchants were selling", Purity.DIRTY, ladder);
        artery.addLocation(alley);

        //alley institutions
        Institution store1 = new Institution("A vegetable store", Purity.MESS, alley);
        Institution store2 = new Institution("A vegetable store", Purity.MESS, alley);
        alley.addLocation(store1, store2);

        //institutions` traders
        Entrepreneur trader1 = store1.getOwner();
        Entrepreneur trader2 = store2.getOwner();

        //institutions` items
        Item tomato = new Item("Tomato", "tomato", 3.0, trader1, Conditions.STALE);
        trader1.onSale(tomato);

        Item potato = new Item("Potato", "potato", 1.0, trader2, Conditions.STALE);
        trader2.onSale(potato);

        //Mr.Charrington
        Entrepreneur charrington = new HBuilder().setName("Mr.Charrington").setAge(63).setBusiness(Business.TRADER).buildEntrepreneur();

        //junk store
        Institution junkStore = new Institution("Junk store", Purity.CLEAR, mainStreet, charrington);
        charrington.moveTo(junkStore);
        Institution stationeryStore = new Institution("stationery store", Purity.CLEAR, mainStreet);

        //stationery store` trader
        Entrepreneur stationeryTrader = stationeryStore.getOwner();
        stationeryTrader.moveTo(stationeryStore);

        //pub
        Institution pub = new Institution("A small, noisy, and dirty pub opposite the stairs", Purity.DIRTY, alley);
        
        //pub`s bartender
        Entrepreneur bartender = pub.getOwner();
        bartender.setBusiness(Business.BARTENDER);

        //oldMan
        Prole oldMan = new HBuilder().setAge(82).setFacialHair(FacialHair.LOBSTERLIKE).setPosture(Posture.HUNCHBACKED).buildProle();
        oldMan.addRemember("Old life", List.of("A pint was just right. Half a litre is too little, and a whole litre is too much for my old bladder.",
            "I remember the \"swells\" in their tall black top hats. If you didn't step off the pavement or tip your cap, they’d shove you or strike you with a stick.",
            "Those rich folks had servants — we called 'em \"Daculas.\" They were a nasty lot, always looking for a reason to put you in your place if you weren't humble enough.",
            "Everything was better back then. The cloth was proper walloping stuff, not like this rubbish we wear now that falls apart in a week.",
            "You ask if life is better now? I can’t tell you. I just remember the price of ale and the ache in my feet. The rest is gone."    
        ));

        //Winston
        OuterPartyMember winston = new HBuilder().setFaith(Faith.PROLES).setName("Winston Smith").setAge(39).setFacialHair(FacialHair.NEAT).setMinistry(Ministries.TRUTH).setTraitor(true).setFaith(Faith.PARTY).buildOuterPartyMember();
        winston.addRemember(junkStore.getName(), List.of("I bought a diary here."));
        winston.addRemember(stationeryStore.getName(), List.of("I bought ink here."));
        winston.addRemember("History textbook", List.of("I copied one paragraph into my diary."));
        winston.addRemember("Ladder", List.of("ladder"));
        winston.moveTo(street);

        //PartyMembers
        OuterPartyMember member1 = new HBuilder().setMinistry(Ministries.PLENTY).buildOuterPartyMember();
        
        Lottery pobeda = member1.makeLottery("pobeda", 500.0, 10);;

        //proles
        Prole prole1 = new HBuilder().setLocation(street).buildProle();
        Prole prole2 = new HBuilder().setLocation(street).buildProle();
        pobeda.getTickets()[0].setOwner(prole1);
        pobeda.getTickets()[1].setOwner(prole2);

        //SCRIPT

        Talk lotteryTalk = new Talk(prole1, prole2, "Lottery");
        lotteryTalk.happen();

        Event checkTalk = new ThoughtEvent(winston);
        checkTalk.visit(lotteryTalk);

        SawEvent faithEvent = new SawEvent(winston);
        faithEvent.visit(street.getPeople());


        Event arterysound = new HeardEvent(winston);
        arterysound.visit(artery);

        winston.moveTo(ladder);

        oldMan.moveTo(pub);

        winston.remember("History textbook");
        Event crazyIdea = new ThoughtEvent(winston);
        crazyIdea.visit(oldMan);
        
    }
}