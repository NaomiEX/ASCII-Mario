package game.effects;

import game.reset.Resettable;

public interface RecurringEffectCapable extends Resettable {
    boolean checkActive();

}
