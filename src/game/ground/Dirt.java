package game.ground;

import engine.positions.Ground;
import game.Status;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	private static final Character DISPLAY_CHAR = '.';

	public Dirt() {
		super(DISPLAY_CHAR);
		this.addCapability(Status.FERTILE);
	}


}
