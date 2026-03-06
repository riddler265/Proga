package attacks.status;

import ru.ifmo.se.pokemon.*;

public final class ScaryFace extends StatusMove {
    public ScaryFace() {
        super(Type.NORMAL, 0.0, 1.0);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        def.setMod(Stat.SPEED, -2);
    }

    @Override protected String describe() {
        return "using Scary Face attack";
    }
}