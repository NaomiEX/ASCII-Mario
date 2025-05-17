package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.items.ConsumableItem;

//FIXME: TOP PRIORITY REFACTOR SO CONSTRUCTOR DOES NOT ACCEPT TWO PARAMS FOR SAME OBJECT
public class ConsumeItemAction extends Action {
    private final ConsumableItem consumable;

    public ConsumeItemAction(ConsumableItem consumable) {
        this.consumable = consumable;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        consumable.consumeEffect(actor, map);
        this.removeConsumableItemFromMap(map);
        return actor + " has consumed " + consumable;
    }

    public void removeConsumableItemFromMap(GameMap map) {
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location thisLocation = map.at(x, y);
                if(thisLocation.getItems().contains(this.consumable)) {
                    thisLocation.removeItem(this.consumable);
                    break;
                }
            }
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + consumable;
    }
}
