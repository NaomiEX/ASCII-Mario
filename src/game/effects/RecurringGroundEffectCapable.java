package game.effects;

import edu.monash.fit2099.engine.positions.Ground;
import game.reset.Resettable;

//TODO: change to return boolean to indicate whether it was effected or not
public interface RecurringGroundEffectCapable extends Resettable {
    boolean checkActive();
    void toggleActive();
    void recurringEffect(Ground ground);
}
