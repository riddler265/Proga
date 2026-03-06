package attacks.status;

import ru.ifmo.se.pokemon.*;

public final class WorkUp extends StatusMove {
    public WorkUp() {
        super(Type.DARK, 0, 1.0);
    }

    @Override protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, 1);
        p.setMod(Stat.SPECIAL_ATTACK, 1);
    }

    @Override protected String describe() {
        return "using Work Up attack";
    }
}