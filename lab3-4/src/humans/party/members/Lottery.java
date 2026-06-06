package humans.party.members;

//java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
//human
import humans.Human;
//item
import items.tickets.Ticket;

public class Lottery {

    //fields    
    protected String name;
    protected double prize;
    protected int winId;
    protected List<Ticket> tickets = new ArrayList<>();
    protected Map<Integer, Human> players = new HashMap<>();
    private static Random random = new Random();

    //constructor
    Lottery(String name, double prize, int tickets) {
        this.name = name;
        this.prize = prize;
        for (int i = 0; i < tickets; i++) {
            this.tickets.add(new Ticket(this, i + 1, 10.0, null));
        }
        this.winId = random.nextInt(tickets) + 1; 
    }

    //getters
    public String getName() {
        return name;
    }

    public double getPrize() {
        return prize;
    }
    
    public int getTicketTotal() {
        return tickets.size();
    }

    public Ticket getTicket(int id) {
        return tickets.get(id - 1);
    }

    public Ticket[] getTickets() {
        return tickets.toArray(new Ticket[0]);
    }

    //human
    public void result() {
        Human winner = players.get(winId);
        if (winner != null) {
            winner.receivePayment(prize/10);
            System.out.println(winner.getName() + " won " + prize);
        } else {
            System.out.println("Nobody wins yet!");
        }
    }

    public void addPlayer(Human human, int id) {
        players.put(Integer.valueOf(id), human);
    }

    //equals, hashCode, toString
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lottery lottery = (Lottery) o;
        return Objects.equals(this.name, lottery.name) && this.prize == lottery.prize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.prize);
    }

    @Override
    public String toString() {
        return "Title - " + this.name + ". Prize: " + this.prize;
    }
}