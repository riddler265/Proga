package attacks.status;

import ru.ifmo.se.pokemon.*;

public final class ThunderWave extends StatusMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0, 0.9);
    }

    @Override protected void applyOppEffects(Pokemon def) {
        if (!def.hasType(Type.ELECTRIC)) {
            Effect.paralyze(def);
            def.setMod(Stat.SPEED, -2);
            if (Math.random() <= 0.25) {
                Effect e = new Effect().chance(1.0).turns(1).attack(0);
                def.addEffect(e);
            }
        }  
    }

    @Override protected String describe() {
        return "using Thunder Wave attack";
    }
}