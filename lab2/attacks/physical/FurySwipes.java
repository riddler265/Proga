package attacks.physical;

import ru.ifmo.se.pokemon.*;

public final class FurySwipes extends PhysicalMove {
    public FurySwipes() {
        super(Type.NORMAL, 18.0, 0.8);
    }

    @Override
    public double calcBaseDamage(Pokemon att, Pokemon def){
        double damage = super.calcBaseDamage(att, def);
        int succesAttack = 0;
        for (int i = 0; i < 2; i++) {
            if (Math.random() <= 3/8) {
                damage+=18;
                succesAttack++;
                }
        }
            
        if (succesAttack >= 2) {
            for (int i = 0; i < 2; i++) {
                if (Math.random() <= 1/8) {
                    damage+=18;
                }
            }
        }

        return damage;
        }

    @Override protected String describe() {
        return "using Fury Swipes attack";
    }
}