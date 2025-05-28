package game.items;

import engine.weapons.Weapon;
import engine.weapons.WeaponItem;

public class WeaponClone extends WeaponItem {
    // does not matter because this weapon is not droppable
    public static final Character DISPLAY_CHARACTER = '?';
    /**
     * Constructor.
     *
     */
    public WeaponClone(Weapon weaponToCopy, int customDamage) {
        super(weaponToCopy.toString(), DISPLAY_CHARACTER,
                customDamage, weaponToCopy.verb(), weaponToCopy.chanceToHit());
        this.togglePortability();
    }


}
