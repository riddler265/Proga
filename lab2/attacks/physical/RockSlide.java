package attacks.physical;

import ru.ifmo.se.pokemon.*;

public final class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75.0, 0.9);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.3) {
            Effect e = new Effect().chance(1.0).turns(1).attack(0);
            def.addEffect(e);
        }
    }

    @Override protected String describe() {
        return "using Rock Slide attack";
    }
}