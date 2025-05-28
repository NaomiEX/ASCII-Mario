package game;

import engine.positions.Location;

public interface RandomDespawnable {

    void randomDespawn(Location location);

    double getDespawnChance();
}
