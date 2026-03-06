package attacks.physical;

import ru.ifmo.se.pokemon.*;

public final class RockTomb extends PhysicalMove {
    public RockTomb() {
        super(Type.ROCK, 60.0, 0.95);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        def.setMod(Stat.SPEED, -1);
    }

    @Override protected String describe() {
        return "using Rock Slide attack";
    }
}