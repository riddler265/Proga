package pokemons;

import attacks.physical.*;
import attacks.status.*;
import ru.ifmo.se.pokemon.*;

public final class Furfrou extends Pokemon {
    public Furfrou(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(75, 80, 60, 65, 90, 102);
        setMove(new Bite(), new WorkUp(),new ThunderWave(), new BabyDollEyes());
    }
}