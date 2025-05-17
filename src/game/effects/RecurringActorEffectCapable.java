package game.effects;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.reset.Resettable;

public interface RecurringActorEffectCapable extends Resettable {
    boolean checkActive();
    void toggleActive();
    void recurringEffect(Actor actor, GameMap map, Display display);
}
