package game.ground.jumpableground;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomDespawnable;
import game.Status;
import game.Utils;
import game.ground.Dirt;
import game.ground.Spreadable;
import game.npcs.enemies.Koopa;

import java.util.ArrayList;
import java.util.List;

public class MatureTree extends Tree implements RandomDespawnable, Spreadable {
    private static final double JUMP_SUCCESS_RATE = 0.7;
    private static final int JUMP_FALL_DAMAGE = 30;
    public static final Character DISPLAY_CHAR = 'T';
    private static final double SPAWN_KOOPA_CHANCE = 0.15;
    private static final double WITHER_CHANCE = 0.2;
    private static final String NAME = "Mature Tree";


    /**
     * Constructor.
     *
     */
    public MatureTree() {
        super(DISPLAY_CHAR,NAME, JUMP_SUCCESS_RATE, JUMP_FALL_DAMAGE);
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
//        if (!this.hasCapability(Status.RESET)) {
//            this.spawn(location);
//            this.randomDespawn(location);
//            if (this.getAge() % 5 == 0) {
//                this.spread(location);
//            }
//
//        } else {
//            this.removeCapability(Status.RESET);
//        }
        if (this.getAge() % 5 == 0) {
            this.spread(location);
        }
        this.spawn(location);
        this.randomDespawn(location);
    }

    @Override
    public void spawn(Location location) {
        if (Utils.performRandomEvent(SPAWN_KOOPA_CHANCE) && !location.containsAnActor()) {
            location.addActor(new Koopa());
        }
    }

    public void spread(Location location) {
        List<Location> fertileLocations = new ArrayList<>();
        for (Exit exit:location.getExits()) {
            Location exitLocation = exit.getDestination();
            if(exitLocation.getGround().hasCapability(Status.FERTILE)) {
                fertileLocations.add(exitLocation);
            }
        }
        if (!fertileLocations.isEmpty()) {
            int randIndex = Utils.randInt(0, fertileLocations.size());
            Location chosenLocation = fertileLocations.get(randIndex);
            chosenLocation.setGround(new Sprout());
        }
    }

    @Override
    public void randomDespawn(Location location) {
        if(Utils.performRandomEvent(this.getDespawnChance())) {
            location.setGround(new Dirt());
        }
    }


    @Override
    public double getDespawnChance() {
        return WITHER_CHANCE;
    }
}
