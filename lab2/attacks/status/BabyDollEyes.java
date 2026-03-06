package attacks.status;

import ru.ifmo.se.pokemon.*;

public final class BabyDollEyes extends StatusMove {
    public BabyDollEyes() {
        super(Type.FAIRY, 0.0, 1.0);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        Effect e = new Effect().chance(1.0).turns(1).stat(Stat.ATTACK, -1);
        def.addEffect(e);
    }

    @Override protected String describe() {
        return "using Baby-Doll Eyes attack";
    }
}