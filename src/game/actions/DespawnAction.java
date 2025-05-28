package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;

public class DespawnAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return actor + " has died.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "despawn " + actor;
    }
}
