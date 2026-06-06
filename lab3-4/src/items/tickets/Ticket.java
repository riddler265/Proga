package items.tickets;

//java
import java.util.Objects;

//enums
import enums.Conditions;
//human
import humans.Human;
import humans.party.members.Lottery;
import humans.proles.Prole;
//item
import items.Item;

public class Ticket extends Item{

  //fields
  private Lottery lottery;
  private int id;

  //constructor
  public Ticket(Lottery lottery, int id, double price, Human owner) {
    super("Ticket " + id, lottery.getName() + "lottery ticket, id - " + id, price, owner, Conditions.NEW);
    this.lottery = lottery;
    this.id = id;
  }

  //getters
  public String getLotteryInfo() {
    return lottery.getName();
  }

  public Lottery getlottery() {
    return lottery;
  }

  public int getId() {
    return id;
  }

  //owner
  @Override
  public void setOwner(Human person) {
    this.owner = person; 
    this.lottery.addPlayer(person, id); 
    if (person instanceof Prole) {
      Prole prole = (Prole) person;
      prole.addLottery(lottery);
    }
  }

  //equals, hashCode, toString
  @Override
    public boolean equals(Object o){
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Ticket item = (Ticket) o;
      return Objects.equals(this.name, item.name) && Objects.equals(this.id, item.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.name, this.id);
    }
}
