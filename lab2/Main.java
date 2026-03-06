import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
  public static void main(String[] args) {
    Battle b = new Battle();

    Furfrou Furfrou = new Furfrou("Furfrou", 13);
    Rattata Rattata = new Rattata("Rattata", 20);
    Raticate Raticate = new Raticate("Raticate", 39);
    Slakoth Slakoth = new Slakoth("Slakoth", 18);
    Vigoroth Vigoroth = new Vigoroth("Vigoroth", 36);
    Slaking Slaking = new Slaking("Slaking", 30);

    b.addAlly(Furfrou);
    b.addAlly(Rattata);
    b.addAlly(Raticate);

    b.addFoe(Slakoth);
    b.addFoe(Vigoroth);
    b.addFoe(Slaking);
    
    b.go();
  }
 }