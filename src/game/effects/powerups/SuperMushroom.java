package game.effects.powerups;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.effects.EffectManager;
import game.effects.RecurringGroundEffectCapable;
import game.ground.jumpableground.JumpableGround;
import game.ground.jumpableground.JumpableGroundManager;
import game.items.ConsumableItem;

public class SuperMushroom extends ConsumableItem implements RecurringGroundEffectCapable {
    public static final String NAME = "Super Mushroom";
    public static final Character DISPLAY_CHAR = '^';
    public static final boolean PICKUPABLE = false;
    public static final boolean DROPPABLE = false;
    private static final int EFFECT_MAX_HP_INCREASE = 50;
    private static final int NO_AFFECTED_GROUND_VALUE = -1;

    private double groundOriginalJumpSuccessRate = NO_AFFECTED_GROUND_VALUE;
    private int groundOriginalJumpFallDamage = NO_AFFECTED_GROUND_VALUE;
    private boolean active;

    /***
     * Constructor.
     */
    public SuperMushroom() {
        super(NAME, DISPLAY_CHAR, PICKUPABLE, DROPPABLE);
    }

    @Override
    public void consumeEffect(Actor actor, GameMap map) {
        super.consumeEffect(actor, map);
        EffectManager.getInstance().addGroundEffect(actor, this);
        actor.increaseMaxHp(EFFECT_MAX_HP_INCREASE);
        actor.addCapability(Status.TALL);
        this.active = true;
    }
//
//    private void immediateReset() { // calling this means that some effect happened, i.e. it must be HighGround
//        HighGroundManager highGroundManager = HighGroundManager.getInstance();
//        HighGround highGround = highGroundManager.findCorrespondingHighGround(ground);
//        highGround.setSuccessRate(this.groundOriginalJumpSuccessRate);
//        highGround.setFallDamage(this.groundOriginalJumpFallDamage);
//    }

    private void recurringRollback(Ground ground) {
        if (this.groundOriginalJumpFallDamage >= 0 && this.groundOriginalJumpSuccessRate >= 0) {
            JumpableGroundManager jumpableGroundManager = JumpableGroundManager.getInstance();
            JumpableGround jumpableGround = jumpableGroundManager.getJumpableGround(ground);
            jumpableGround.setSuccessRate(this.groundOriginalJumpSuccessRate);
            jumpableGround.setFallDamage(this.groundOriginalJumpFallDamage);
            this.groundOriginalJumpFallDamage = NO_AFFECTED_GROUND_VALUE;
            this.groundOriginalJumpSuccessRate = NO_AFFECTED_GROUND_VALUE;
        }
    }

    @Override
    public void resetInstance(Location location) {
        if(location != null) this.recurringRollback(location.getGround());
        if (!this.checkActive()) {
            this.getConsumer().removeCapability(Status.TALL);
            EffectManager.getInstance().removeGroundEffect(this.getConsumer(),this);
        }
    }


    @Override
    public void recurringEffect(Ground ground) {
        JumpableGroundManager jumpableGroundManager = JumpableGroundManager.getInstance();
        if (jumpableGroundManager.isJumpableGround(ground)) {
            JumpableGround jumpableGround = jumpableGroundManager.getJumpableGround(ground);
            this.groundOriginalJumpSuccessRate = jumpableGround.getSuccessRate();
            this.groundOriginalJumpFallDamage = jumpableGround.getFallDamage();
//            RecurringGroundEffectCapable.affected.put(new HighGround(highGround), highGround);
            jumpableGround.setSuccessRate(1);
            jumpableGround.setFallDamage(0);
        }
    }


    @Override
    public boolean checkActive() {
        return !this.getConsumer().hasCapability(Status.RECENTLY_HURT) && this.active;
    }

    public void toggleActive() {
        this.active = !this.active;
    }

}
