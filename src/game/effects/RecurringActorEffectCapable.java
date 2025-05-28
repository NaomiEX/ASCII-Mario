package game.effects;

import engine.actors.Actor;
import engine.displays.Display;
import engine.positions.GameMap;
import game.reset.Resettable;

public interface RecurringActorEffectCapable extends Resettable {
    boolean checkActive();
    void toggleActive();
    void recurringEffect(Actor actor, GameMap map, Display display);
}
