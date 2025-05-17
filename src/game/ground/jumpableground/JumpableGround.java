package game.ground.jumpableground;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.actions.JumpAction;
import game.effects.EffectManager;

import java.util.List;
import java.util.Objects;

public abstract class JumpableGround extends Ground implements Jumpable {
    private double successRate;
    private int fallDamage;
    private final String name;
    private boolean jumpable;
//    private final
    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     * @param name
     */
    public JumpableGround(char displayChar, String name, double successRate, int fallDamage) {
        super(displayChar);
        this.name = name;
        this.successRate = successRate;
        this.fallDamage = fallDamage;
//        this.addCapability(Status.TALL);
        this.jumpable = true;
        JumpableGroundManager.getInstance().addJumpableGround(this);
    }

//    // copy original
//    public HighGround(HighGround original) {
//        super(original.getDisplayChar());
//        this.name = original.getName();
//        this.successRate = original.getSuccessRate();
//        this.fallDamage = original.getFallDamage();
//        this.addCapability(Status.TALL);
//        this.jumpable = true;
//    }


    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        // effect here
        EffectManager.getInstance().run(actor, this);

        ActionList actions = super.allowableActions(actor, location, direction);

        // preemptively add canActorEnter
        List<Exit> exits = location.getExits();
        Location playerLoc = null;

        for (Exit exit : exits) {
            if (Objects.equals(exit.getName(), direction)) {
                String hotKey = exit.getHotKey();
                Action moveAction = location.getMoveAction(actor, direction, hotKey);
                if (moveAction != null) actions.add(moveAction);
            }
        }

        if (this.jumpable && (!location.containsAnActor())) {
            actions.add(new JumpAction(this, location, direction));
        }
        EffectManager.getInstance().resetGroundEffects(actor, location);
        return actions;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        //TODO: refactor so that it would only override for the Actor that got the powerup
        // only player can override this
        return !this.jumpable;
    }

    /* getters */

    @Override
    public double getSuccessRate() {
        return successRate;
    }

    @Override
    public int getFallDamage() {
        return fallDamage;
    }

    public String getName() {
        return name;
    }

    public boolean isJumpable() {
        return jumpable;
    }

    public void toggleJumpable() { this.jumpable = !this.jumpable; }

    /* setters */

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public void setFallDamage(int fallDamage) {
        this.fallDamage = fallDamage;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
