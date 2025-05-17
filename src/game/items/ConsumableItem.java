package game.items;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpItemAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeItemAction;
import game.items.Consumable;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsumableItem extends Item implements Consumable {
    private final boolean droppable;
    private Actor consumer;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     */
    public ConsumableItem(String name, char displayChar, boolean pickupable, boolean droppable) {
        super(name, displayChar, pickupable);
        this.droppable = droppable;
        this.addAction(new ConsumeItemAction(this));
    }

    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        PickUpItemAction pickUpAction = super.getPickUpAction(actor);
        if (droppable) this.togglePortability();
        return pickUpAction;
    }

    @Override
    public void consumeEffect(Actor actor, GameMap map) {
        this.setConsumer(actor);
    }

//    @Override
//    public List<Action> getAllowableActions() {
//        List<Action> actionList = new ArrayList<>();
//        actionList.add(new ConsumeItemAction(this));
//        return actionList;
//    }

    public Actor getConsumer() {
        return consumer;
    }

    public void setConsumer(Actor consumer) {
        this.consumer = consumer;
    }
}
