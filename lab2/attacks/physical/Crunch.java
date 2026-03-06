package attacks.physical;

import ru.ifmo.se.pokemon.*;

public final class Crunch extends PhysicalMove {
    public Crunch() {
        super(Type.DARK, 80.0, 1.0);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        if (Math.random() <= 0.2) {
            def.setMod(Stat.DEFENSE, -1);
        }
    }

    @Override protected String describe() {
        return "using Crunch attack";
    } 

}