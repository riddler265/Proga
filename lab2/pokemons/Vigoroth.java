package pokemons;

import attacks.physical.*;
import attacks.status.*;
import ru.ifmo.se.pokemon.*;

public class Vigoroth extends Slakoth {
    public Vigoroth(String name, int level) {
        super(name, level);
        setStats(80,80,80,55,55,90);
        addMove(new FurySwipes());
    }
}