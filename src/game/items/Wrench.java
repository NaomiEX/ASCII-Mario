package game.items;

import engine.weapons.WeaponItem;

public class Wrench extends WeaponItem {
    public static final String NAME = "Wrench";
    private static final Character DISPLAY_CHARACTER = 'p';
    private static final int DAMAGE = 50;
    private static final String ATTACK_VERB = "whacks";
    private static final int ACCURACY = 80;

    /**
     * Constructor.
     *
     */
    public Wrench() {
        super(NAME, DISPLAY_CHARACTER, DAMAGE, ATTACK_VERB, ACCURACY);
    }
}
