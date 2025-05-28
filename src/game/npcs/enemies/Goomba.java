package game.npcs.enemies;


import engine.actions.Action;
import engine.actions.ActionList;
import engine.actions.DoNothingAction;
import engine.displays.Display;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.RandomDespawnable;
import game.Status;
import game.Utils;

/**
 * A little fungus guy.
 */
public class Goomba extends Enemy implements RandomDespawnable {
	private static final String NAME = "Goomba";
	private static final Character DISPLAY_CHARACTER = 'g';
	private static final int INITIAL_HIT_POINTS = 20;
	private static final int ATTACK_DAMAGE = 10;
	private static final String ATTACK_VERB = "kicks";
	private static final double DESPAWN_CHANCE = 0.1;

	/**
	 * Constructor.
	 */
	public Goomba() {
		super(NAME, DISPLAY_CHARACTER, INITIAL_HIT_POINTS);
	}

	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		Action defaultAction = super.playTurn(actions, lastAction, map, display);
		if(!this.hasCapability(Status.RESET)) {
			this.randomDespawn(map.locationOf(this));
		}
		return map.contains(this) ? defaultAction : new DoNothingAction();
	}

	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(ATTACK_DAMAGE, ATTACK_VERB);
	}

	public void randomDespawn(Location location) {
		if (Utils.performRandomEvent(this.getDespawnChance())) {
			location.map().removeActor(this);
			EnemyManager.getInstance().removeEnemy(this);
		}
	}

	@Override
	public double getDespawnChance() {
		return DESPAWN_CHANCE;
	}
}
