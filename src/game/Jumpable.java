package game;

import edu.monash.fit2099.engine.displays.Printable;
import game.actions.JumpAction;

public interface Jumpable {
    double getSuccessRate();

    int getFallDamage();
}
