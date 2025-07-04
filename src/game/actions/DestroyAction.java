package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.Destroyable;

public class DestroyAction extends Action {
    private Destroyable target;
    private String direction;

    /**
     * Constructor.
     *
     * @param target    the Actor to attack
     * @param direction
     */
    public DestroyAction(Destroyable target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        this.target.destroy(actor, map);
        return actor + " has destroyed " + this.target + " in the " + direction;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "destroy " + this.target + ".";
    }
}
