package game.ground.jumpableground;

import engine.positions.Location;
import game.Utils;
import game.npcs.enemies.Goomba;
import game.Growable;

public class Sprout extends Tree implements Growable {
    private static final double JUMP_SUCCESS_RATE = 0.9;
    private static final int JUMP_FALL_DAMAGE = 10;
    private static final double SPAWN_GOOMBA_CHANCE = 0.1;
    public static final Character DISPLAY_CHAR = '+';
    private static final int GROW_UP_AGE = 10;
    private static final String NAME = "Sprout";

    /**
     * Constructor
     *
     */
    public Sprout() {
        super(DISPLAY_CHAR, NAME, JUMP_SUCCESS_RATE, JUMP_FALL_DAMAGE);
//        this.setSpawnableEntity(new Goomba(), SPAWN_GOOMBA_CHANCE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
//        if (!this.hasCapability(Status.RESET)) {
//
//            this.grow(location);
//            if (!location.containsAnActor()) {
//                this.spawn(location);
//            }
//        } else {
//            this.removeCapability(Status.RESET);
//        }
        this.grow(location);
        this.spawn(location);
    }

    @Override
    public void spawn(Location location) {
        if (!location.containsAnActor() && Utils.performRandomEvent(SPAWN_GOOMBA_CHANCE)) {
            location.addActor(new Goomba());
        }
    }

    @Override
    public void grow(Location location) {
        if(this.getAge() >= GROW_UP_AGE) {
            //replace with Sapling
            location.setGround(new Sapling());
        }
    }


}
