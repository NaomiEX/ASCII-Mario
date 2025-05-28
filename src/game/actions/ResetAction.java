package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.reset.ResetManager;

public class ResetAction extends Action {

    private static final String RESET_HOTKEY = "r";

    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.getInstance().run();
        return "Game has been reset.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Reset the game";
    }

    @Override
    public String hotkey() {
        return RESET_HOTKEY;
    }
}
