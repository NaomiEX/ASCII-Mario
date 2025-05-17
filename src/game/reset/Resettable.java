package game.reset;

import edu.monash.fit2099.engine.capabilities.Capable;
import edu.monash.fit2099.engine.positions.Location;

public interface Resettable extends Capable {
    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
     * TODO: execute this method in a reset manager later.
     * @param location
     */
    void resetInstance(Location location);

    /**
     * a default interface method that register current instance to the Singleton manager.
     * It allows corresponding class uses to be affected by global reset
     * TODO: Use this method at the constructor of `this` instance.
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}
