package game.ground.jumpableground;

import engine.positions.Location;
import game.SpawnCapable;
import game.ground.Dirt;
import game.reset.Resettable;
import game.Status;
import game.Utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Tree extends JumpableGround implements Resettable, SpawnCapable {
    private static final double RESET_CHANCE = 0.5;

    private static final Map<Character, Double> SPAWNABLE_ENTITIES = new HashMap<>();
    private int age;

    /**
     * Constructor.
     *
     */
    public Tree(Character displayChar, String name, double jumpSuccessRate, int fallDamage) {
        super(displayChar, name, jumpSuccessRate, fallDamage);
        this.age = 0;
        this.registerInstance();
    }

    @Override
    public void tick(Location location) {
        this.age++;
        if(this.hasCapability(Status.RESET)) {
            this.resetInstance(location);
        }
    }


    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    protected final void setSpawnableEntity(Printable spawnableEntity, Double spawnableChance) {
//        SPAWNABLE_ENTITIES.put(spawnableEntity.getDisplayChar(), spawnableChance);
//    }
//
//    protected final void setSpawnableEntities(Map<Printable, Double> spawnableEntities) {
//        for (Printable spawnableEntity: spawnableEntities.keySet()) {
//            SPAWNABLE_ENTITIES.put(spawnableEntity.getDisplayChar(), spawnableEntities.get(spawnableEntity));
//        }
//    }
//
//    @Override
//    public void spawn(Location location) {
//        for
//    }
//
//    @Override
//    public double getSpawnChance(Character entityDisplayChar) {
//        return SPAWNABLE_ENTITIES.get(entityDisplayChar);
//    }

    @Override
    public void resetInstance(Location location) {
        if (Utils.performRandomEvent(RESET_CHANCE)) {
            location.setGround(new Dirt());
        }
    }
}
