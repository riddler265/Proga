package pokemons;

import attacks.physical.*;
import attacks.status.*;
import ru.ifmo.se.pokemon.*;

public final class Raticate extends Rattata {
    public Raticate(String name, int level) {
        super(name, level);
        setStats(55, 81, 60, 50, 70, 97);
        addMove(new ScaryFace());
    }    
}