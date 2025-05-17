package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ResetAction;
import game.items.currency.Coin;
import game.items.currency.WorldBank;
import game.effects.EffectManager;
import game.reset.Resettable;

import java.util.Objects;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable {

	private final Menu menu = new Menu();
//	private final Wallet wallet;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.registerInstance();
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions

		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		if (!this.hasCapability(Status.RESET)) {
			actions.add(new ResetAction());
//			this.resetInstance(map.locationOf(this));
//		} else {
		}

		display.println(this + " HP: " + this.printHp());

		display.println("-----INVENTORY-----");
		for (int i = 0; i < this.getInventory().size(); i++) {
			display.println(this.getInventory().get(i).toString());
		}
		Coin balance = WorldBank.getInstance().getBalance(this, "Coin");
		display.println("Wallet: " + (balance != null ? balance.getValue() : 0));

//		AbstractSet<RecurringActorEffectCapable> playerPowerups = EffectManager.getInstance().getActorEffects(this);
//		if (playerPowerups != null) {
//			for (RecurringActorEffectCapable powerup : playerPowerups) {
//				powerup.recurringEffect(this);
//			}
//		}

		EffectManager.getInstance().run(this, this, map, display);
		EffectManager.getInstance().resetActorEffects(this, map.locationOf(this));
		Action actionChosen =  menu.showMenu(this, actions, display);
		// after player's chosen an action (effectively end of their turn),
		// this is preparation for next turns
//		if (playerPowerups != null) {
//
//			for (RecurringActorEffectCapable powerup : playerPowerups) {
//				powerup.resetInstance(map.locationOf(this));
//			}
//		}
		EffectManager.getInstance().resetActorEffects(this, map.locationOf(this));
		// removes recently hurt
		if (this.hasCapability(Status.RECENTLY_HURT)) this.removeCapability(Status.RECENTLY_HURT);
		if (Objects.equals(actionChosen.hotkey(), "r")) this.resetInstance(map.locationOf(this));
		return actionChosen;
	}



	//TODO: change later to make it extensible
	@Override
	public void addItemToInventory(Item item) {
		WorldBank worldBank = WorldBank.getInstance();
		if (worldBank.isValidCurrency(item)) {
			Coin currency = worldBank.getCorrespondingCoin(item);
			worldBank.addCoin(this, currency);
			return;
		}
		super.addItemToInventory(item);
	}

	@Override
	public void hurt(int points) {
		super.hurt(points);
		this.addCapability(Status.RECENTLY_HURT);
		System.out.println("JUST HURT JUST HURT!!");
		EffectManager.getInstance().resetGroundEffects(this, null);
	}

	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}


	@Override
	public void resetInstance(Location location) {
		this.heal(this.getMaxHp());
		EffectManager.getInstance().forceReset(this, location);
		this.addCapability(Status.RESET);
	}

}
