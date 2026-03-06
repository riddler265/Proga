package pokemons;

import attacks.physical.*;
import attacks.status.*;
import ru.ifmo.se.pokemon.*;

public class Slakoth extends Pokemon {
    public Slakoth(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(60,60,60,35,35,30);
        setMove(new RockSlide(), new RockTomb());
    }
}