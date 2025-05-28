package game.ground.jumpableground;

import engine.positions.Location;
import game.Growable;
import game.Utils;
import game.items.currency.Coin;

public class Sapling extends Tree implements Growable {
    private static final double JUMP_SUCCESS_RATE = 0.8;
    private static final int JUMP_FALL_DAMAGE = 20;
    public static final Character DISPLAY_CHAR = 't';
    private static final int GROW_UP_AGE = 10;
    private static final double SPAWN_COIN_CHANCE = 0.1;
    private static final int SPAWN_COIN_VALUE = 20;
    private static final String NAME = "Sapling";

    /**
     * Constructor.
     *
     */
    public Sapling() {
        super(DISPLAY_CHAR, NAME, JUMP_SUCCESS_RATE, JUMP_FALL_DAMAGE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
//        if (!this.hasCapability(Status.RESET)) {
//            this.grow(location);
//            this.spawn(location);
//
//        } else {
//            this.removeCapability(Status.RESET);
//        }
        this.grow(location);
        this.spawn(location);
    }

    @Override
    public void grow(Location location) {
        if(this.getAge() >= GROW_UP_AGE) {
            location.setGround(new MatureTree());

        }
    }

    @Override
    public void spawn(Location location) {
        if (Utils.performRandomEvent(SPAWN_COIN_CHANCE)) {
            location.addItem(new Coin(SPAWN_COIN_VALUE));
        }
    }
}
