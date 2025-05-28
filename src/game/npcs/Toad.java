package game.npcs;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actions.DoNothingAction;
import engine.actors.Actor;
import engine.displays.Display;
import engine.items.Item;
import engine.positions.GameMap;
import game.actions.SpeakAction;
import game.actions.TradeAction;
import game.items.currency.Coin;
import game.items.Wrench;
import game.effects.powerups.PowerStar;
import game.effects.powerups.SuperMushroom;

import java.util.*;

public class Toad extends Actor {
    private static final String NAME = "Toad";
    private static final Character DISPLAY_CHARACTER = 'O';
    private static final int HIT_POINTS = 1;
    private final Map<Item, Coin> wares = new HashMap<>();
    private static final int POWER_STAR_COST = 60;
    private static final int SUPER_MUSHROOM_COST = 40;
    private static final int WRENCH_COST = 20;

    /**
     * Constructor.
     *
     */
    public Toad() {
        super(NAME, DISPLAY_CHARACTER, HIT_POINTS);
        this.wares.put(new PowerStar(), new Coin(POWER_STAR_COST));
        this.wares.put(new SuperMushroom(), new Coin(SUPER_MUSHROOM_COST));
        this.wares.put(new Wrench(), new Coin(WRENCH_COST));
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList retActions = new ActionList(new SpeakAction(this)); //TODO: add speak action
        for (Item ware: this.wares.keySet()) {
            Coin cost = this.wares.get(ware);
            retActions.add(new TradeAction(ware, cost));
        }
        return retActions;
    }

}
