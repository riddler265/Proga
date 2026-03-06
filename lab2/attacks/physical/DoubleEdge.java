package attacks.physical;

import ru.ifmo.se.pokemon.*;

public final class DoubleEdge extends PhysicalMove {
    public DoubleEdge() {
        super(Type.NORMAL, 120.0, 1.0);
    }

    @Override protected void applySelfDamage(Pokemon att, double damage) {
        att.setMod(Stat.HP, (int) Math.round(damage/3));
    }

    @Override public String describe() {
        return "using Double-Edge attack";
    }
}