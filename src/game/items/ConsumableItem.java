package game.items;

import engine.actors.Actor;
import engine.items.Item;
import engine.items.PickUpItemAction;
import engine.positions.GameMap;
import game.actions.ConsumeItemAction;

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
