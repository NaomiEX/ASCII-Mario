package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.items.Item;
import engine.positions.GameMap;
import game.Utils;
import game.items.Wrench;
import game.npcs.Monologue;

import java.util.Arrays;
import java.util.List;

public class SpeakAction extends Action {
    private final Actor speakTarget;

    public SpeakAction(Actor speakTarget) {
        this.speakTarget = speakTarget;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        List<String> monologueOptions = Arrays.asList(Monologue.getMonologueOptions());

        //TODO: filter when PowerStar is active
        for(Item item: actor.getInventory()) {
            if (item.toString().equals(Wrench.NAME)) {
                monologueOptions.remove(Monologue.KOOPA_DESTROY_TIP);
            }
        }
        int randIndex = Utils.randIndex(monologueOptions);
        return speakTarget + ": " + monologueOptions.get(randIndex);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " talks with " + speakTarget;
    }
}
