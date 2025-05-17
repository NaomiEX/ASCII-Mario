package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public interface Destroyable {

    void destroy(Actor actor, GameMap map);

    boolean canBeDestroyed(Actor actor);
}
