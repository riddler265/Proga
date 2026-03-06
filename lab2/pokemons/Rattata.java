package pokemons;

import attacks.physical.*;
import attacks.status.*;
import ru.ifmo.se.pokemon.*;

public class Rattata extends Pokemon {
    public Rattata(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(30, 56, 35, 25, 35, 72);
        setMove(new DoubleEdge(), new Crunch(), new ThunderWave());
    }
}