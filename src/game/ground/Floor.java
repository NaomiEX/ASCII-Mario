package game.ground;

import engine.actors.Actor;
import engine.positions.Ground;
import game.npcs.enemies.Enemy;
import game.npcs.enemies.EnemyManager;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {
	private static final Character DISPLAY_CHARACTER = '_';
	public Floor() {
		super(DISPLAY_CHARACTER);
	}

	@Override
	public boolean canActorEnter(Actor actor) {
		for(Enemy enemy : EnemyManager.getInstance().getAllEnemies()) {
			if(enemy == actor) {
				 	return false;
			}
		}
		return super.canActorEnter(actor);
	}
}
