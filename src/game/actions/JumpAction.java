package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Jumpable;
import game.Utils;

public class JumpAction extends Action {
    private final Jumpable jumpableEntity;
    private final Location jumpToLocation;
    private final String direction;
    private double successRate;
    private int fallDamage;

    public JumpAction(Jumpable jumpableEntity, Location jumpToLocation, String direction) {
        this.jumpableEntity = jumpableEntity;
        this.jumpToLocation = jumpToLocation;
        this.direction = direction;
        this.successRate = jumpableEntity.getSuccessRate();
        this.fallDamage = jumpableEntity.getFallDamage();
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        //TODO:remove
        System.out.println("SUCCESS RATE: " + this.successRate);
        System.out.println("FALL DAMAGE: " + this.fallDamage);
        String result;
        if (Utils.performRandomEvent(this.getSuccessRate())) {
            map.moveActor(actor, this.jumpToLocation);
            result = "There it is! " + actor + "'s iconic move.\n" +
                      actor + " has successfully jumped on " + this.getJumpableEntity() + " in the " + this.getDirection() + ".";
        } else { // fail the jump
            actor.hurt(this.getFallDamage());
            result = "Aww man, it seems that " + actor + " is out of practice...\n" +
                     actor + " failed to jump on " + this.getJumpableEntity() + " in the " + this.getDirection() +
                     " and has received " + this.getFallDamage() + " fall damage.";
        }
        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " jumps to the " + direction + " " + jumpableEntity;
    }

    /* getters */
    public Jumpable getJumpableEntity() {
        return jumpableEntity;
    }

    public Location getJumpToLocation() {
        return jumpToLocation;
    }

    public String getDirection() {
        return direction;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getFallDamage() {
        return fallDamage;
    }

    /* setters */
    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public void setFallDamage(int fallDamage) {
        this.fallDamage = fallDamage;
    }
}
