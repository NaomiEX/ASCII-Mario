package game.ground.jumpableground;

public class Wall extends JumpableGround {
	public static final Character DISPLAY_CHARACTER= '#';
	private static final double JUMP_SUCCESS_RATE = 0.9;
	private static final int JUMP_FALL_DAMAGE = 10;
	private static final String NAME = "Wall";

	/**
	 * Constructor.
	 *
	 */
	public Wall() {
		super(DISPLAY_CHARACTER, NAME, JUMP_SUCCESS_RATE, JUMP_FALL_DAMAGE);
	}

	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}
