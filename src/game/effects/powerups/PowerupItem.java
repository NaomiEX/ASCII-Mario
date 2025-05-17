package game.effects.powerups;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import game.effects.RecurringEffectCapable;

public abstract class PowerupItem extends Item implements RecurringEffectCapable {
    private Actor affectedActor;
    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public PowerupItem(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        PickUpItemAction pickUpAction = super.getPickUpAction(actor);
        this.togglePortability(); // after picking up it can no longer be dropped
        return pickUpAction;
    }

    public Actor getAffectedActor() {
        return affectedActor;
    }

    public void setAffectedActor(Actor affectedActor) {
        this.affectedActor = affectedActor;
    }


}
