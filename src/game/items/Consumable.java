package game.items;

import engine.actors.Actor;
import engine.positions.GameMap;

public interface Consumable {

    void consumeEffect(Actor actor, GameMap map);

}
