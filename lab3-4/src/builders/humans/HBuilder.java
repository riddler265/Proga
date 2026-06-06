package builders.humans;

import java.util.Random;

import enums.Business;
import enums.FacialHair;
import enums.Faith;
import enums.Hope;
import enums.Ministries;
import enums.Posture;
import humans.party.members.InnerPartyMember;
import humans.party.members.OuterPartyMember;
import humans.proles.Entrepreneur;
import humans.proles.Prole;
import locations.institutions.Institution;
import locations.locations.Location;

public class HBuilder {

    //HUMAN
    //fields
    protected String[] names = {
        "Liam", "Oliver", "Ethan", "Sebastian", "Alexander",
        "Julian", "Theodore", "Olivia", "Emma", "Charlotte",
        "Amelia", "Sophia", "Isabella", "Aurora", "Hazel"
    };

    Random random = new Random();
    int randomIndex;
    protected String name;
    protected int age = 30;
    protected boolean rectified = false;
    protected Faith faith = Faith.PARTY;
    protected double money = 100.0;
    protected FacialHair facialHair = FacialHair.NONE;
    protected Posture posture = Posture.STRAIGHT;
    protected Location location = null;

    //PROLE
    protected boolean lotteryAddiction = true;
    protected Hope hope = Hope.TOTAL;

    //ENTREPREUNEUR
    protected Business business = Business.TRADER;
    protected Institution institution = null;

    //PARTYMEMBER
    protected Ministries ministry = Ministries.PEACE;
    protected boolean traitor = false;

    //constructor
    public HBuilder() {
        randomIndex = random.nextInt(names.length);
        this.name = names[randomIndex];   
    }

    //getters
    //HUMAN
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getRectified() {
        return rectified;
    }

    public Faith getFaith() {
        return faith;
    }

    public double getMoney() {
        return money;
    }

    public FacialHair getFacialHair() {
        return facialHair;
    }

    public Posture getPosture() {
        return posture;
    }

    public Location getLocation() {
        return location;
    }

    //PROLE
    public boolean getLotteryAddiction() {
        return lotteryAddiction;
    }

    public Hope getHope() {
        return hope;
    }

    //ENTREPREUNEUR
    public Business getBusiness() {
        return business;
    }

    public Institution getInstitution() {
        return institution;
    }

    //PARTYMEMBER
    public Ministries getMinistry() {
        return ministry;
    } 

    public boolean getTraitor() {
        return traitor;
    }

    //setters
    //HUMAN
    public HBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public HBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public HBuilder setRectify(boolean rectified) {
        this.rectified = rectified;
        return this;
    }

    public HBuilder setFaith(Faith faith) {
        this.faith = faith;
        return this;
    }

    public HBuilder setMoney(double money) {
        this.money = money;
        return this;
    }

    public HBuilder setFacialHair(FacialHair facialHair) {
        this.facialHair = facialHair;
        return this;
    }

    public HBuilder setPosture(Posture posture) {
        this.posture = posture;
        return this;
    }

    public HBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    //PROLE
    public HBuilder setLotteryAddiction(boolean lotteryAddiction) {
        this.lotteryAddiction = lotteryAddiction;
        return this;
    }

    public HBuilder setHope(Hope hope) {
        this.hope = hope;
        return this;
    }

    //ENTREPREUNEUR
    public HBuilder setBusiness(Business business) {
        this.business = business;
        return this;
    }

    public HBuilder setInstitution(Institution institution) {
        this.institution = institution;
        return this;
    }

    //PARTYMEMBER
    public HBuilder setMinistry(Ministries ministry) {
        this.ministry = ministry;
        return this;
    }

    public HBuilder setTraitor(boolean traitor) {
        this.traitor = traitor;
        return this;
    }

    //build
    public Prole buildProle() {
        return new Prole(this);
    }

    public Entrepreneur buildEntrepreneur() {
        return new Entrepreneur(this);
    }

    public OuterPartyMember buildOuterPartyMember() {
        return new OuterPartyMember(this);
    }

    public InnerPartyMember buildInnerPartyMember() {
        return new InnerPartyMember(this);
    }

}
