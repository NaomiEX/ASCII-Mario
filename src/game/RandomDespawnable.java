package game;

import edu.monash.fit2099.engine.positions.Location;

public interface RandomDespawnable {

    void randomDespawn(Location location);

    double getDespawnChance();
}
