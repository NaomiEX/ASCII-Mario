package game;

import engine.actors.Actor;
import engine.positions.GameMap;

public interface Destroyable {

    void destroy(Actor actor, GameMap map);

    boolean canBeDestroyed(Actor actor);
}
