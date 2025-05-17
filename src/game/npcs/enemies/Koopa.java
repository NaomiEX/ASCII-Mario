package game.npcs.enemies;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Destroyable;
import game.Status;
import game.actions.DestroyAction;
import game.effects.powerups.SuperMushroom;

import java.util.Objects;

public class Koopa extends Enemy implements Destroyable {
    private static final String NAME = "Koopa";
    private static final Character ACTIVE_DISPLAY_CHARACTER = 'K';
    private static final Character DORMANT_DISPLAY_CHARACTER = 'D';
    private static final int INITIAL_HIT_POINTS = 100;
    private static final String DESTROY_WEAPON_NAME = "Wrench";
//    private static final int DORMANT_BEHAVIOUR_PRIORITY = 100; // arbitrarily large number

    public Koopa() {
        super(NAME, ACTIVE_DISPLAY_CHARACTER, INITIAL_HIT_POINTS);
        this.addItemToInventory(new SuperMushroom());
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList defaultActions = super.allowableActions(otherActor, direction, map);
        if (this.hasCapability(Status.DORMANT)) {
            defaultActions.clear();
            if (this.canBeDestroyed(otherActor)) {
                defaultActions.add(new DestroyAction(this, direction));
            }
        }
        return defaultActions;
    }

    @Override
    public boolean isConscious() {
        boolean active = super.isConscious();
        if (!active) {
            this.setDisplayChar(DORMANT_DISPLAY_CHARACTER);
            this.addCapability(Status.DORMANT);
            this.clearBehaviours(); // by default if no behaviour, does nothing
        }
        return active;
    }

    @Override
    public boolean canBeDestroyed(Actor actor) {
        boolean destroyable = false;
        for (Item inventoryItem : actor.getInventory()) {
            if (Objects.equals(inventoryItem.toString(), DESTROY_WEAPON_NAME)) {
                destroyable = true;
                break;
            }
        }
        return destroyable;
    }

    @Override
    public void destroy(Actor actor, GameMap map) {
        map.removeActor(this);
    }

    @Override
    public String toString() {
        return super.toString() + (this.hasCapability(Status.DORMANT) ? " (dormant)" : "");
    }
}
