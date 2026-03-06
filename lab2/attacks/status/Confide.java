package attacks.status;

import ru.ifmo.se.pokemon.*;

public final class Confide extends StatusMove {
    public Confide() {
        super(Type.NORMAL, 0, 1.0);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        def.setMod(Stat.SPECIAL_DEFENSE, -1);
    }

    @Override protected String describe() {
        return "using Confide attack";
    }
}