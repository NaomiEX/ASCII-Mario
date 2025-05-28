package game.effects.powerups;

import engine.actors.Actor;
import engine.displays.Display;
import engine.items.Item;
import engine.positions.Exit;
import engine.positions.GameMap;
import engine.positions.Ground;
import engine.positions.Location;
import engine.weapons.Weapon;
import engine.weapons.WeaponItem;
import game.Status;
import game.effects.EffectManager;
import game.effects.RecurringActorEffectCapable;
import game.effects.RecurringGroundEffectCapable;
import game.ground.Dirt;
import game.ground.jumpableground.JumpableGround;
import game.ground.jumpableground.JumpableGroundManager;
import game.items.ConsumableItem;
import game.items.currency.Coin;
import game.items.WeaponClone;
import game.npcs.enemies.EnemyManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PowerStar extends ConsumableItem implements RecurringActorEffectCapable, RecurringGroundEffectCapable {
    private static final String NAME = "Power Star";
    public static final Character DISPLAY_CHAR = '*';
    private static final boolean PICKUPABLE = false;
    private static final boolean DROPPABLE = false;

    private static final int INITIAL_TIME_LEFT = 10;
    private static final int HEAL_AMOUNT = 200;
    private static final int UNEARTHED_COIN_VALUE = 5;
    private static final int NO_DAMAGE = 0;
    private static final int MAX_DAMAGE = Integer.MAX_VALUE;
    private int timeLeft;
    private boolean active;
    /***
     * Constructor.
     */
    public PowerStar() {
        super(NAME, DISPLAY_CHAR, PICKUPABLE, DROPPABLE);
        this.timeLeft = INITIAL_TIME_LEFT;
        this.active = true;
    }

    // when on ground
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        this.decrementTime();
        this.despawn(currentLocation);
    }

    // when in inventory
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation, actor);
        this.decrementTime();
        this.despawn(actor);
    }

    @Override
    public void consumeEffect(Actor actor, GameMap map) {
        super.consumeEffect(actor, map);
        EffectManager.getInstance().addGroundEffect(this.getConsumer(), this);
        EffectManager.getInstance().addActorEffect(this.getConsumer(), this);
        actor.heal(HEAL_AMOUNT);
        this.setTimeLeft(INITIAL_TIME_LEFT);
        this.active = true;
    }


    @Override
    public void resetInstance(Location location) {
        if (location != null) {
            this.resetGroundInstance(location.getGround());
            if(!this.checkActive()) {

                if (location.containsAnActor()){
                    this.resetAllActors(location.map());
                    if (location.getActor().hasCapability(Status.HOSTILE_TO_ENEMY)) {
                        for(Exit exit: location.getExits()) {
                            this.resetGroundInstance(exit.getDestination().getGround());
                        }
                    }
                }
                EffectManager effectManager = EffectManager.getInstance();
                effectManager.removeGroundEffect(this.getConsumer(), this);
                effectManager.removeActorEffect(this.getConsumer(), this);
            }
        }
    }

    private void resetGroundInstance(Ground ground) {
        JumpableGroundManager jumpableGroundManager = JumpableGroundManager.getInstance();
        if (jumpableGroundManager.isJumpableGround(ground)) {
            JumpableGround jumpableGroundEq = jumpableGroundManager.getJumpableGround(ground);
            if(!jumpableGroundEq.isJumpable()) jumpableGroundEq.toggleJumpable();
        }
    }

    private void resetAllActors(GameMap map) {
        for (int x: map.getXRange()) {
            for (int y: map.getYRange()) {
                if (map.at(x, y).containsAnActor()) {
                    this.resetActorInstance(map.at(x, y).getActor());
                }
            }
        }
    }

    private void resetActorInstance(Actor actor) {
        List<Item> actorInventory = actor.getInventory();
        if (actorInventory != null) {
            List<Item> allClones = new ArrayList<>();
            for (Item actorItem : actorInventory) {
                if (actorItem.getDisplayChar() == WeaponClone.DISPLAY_CHARACTER) {
                    allClones.add(actorItem);
                }
            }
            for(Item cloneItem: allClones) {
                actor.removeItemFromInventory(cloneItem);
            }
        }
    }

    private void despawn(Location location) {
        if (!this.checkActive()) location.removeItem(this);
    }

    private void despawn(Actor actor) {
        if (!this.checkActive()) {
            List<Item> playerInventory = actor.getInventory();
            if (playerInventory != null && playerInventory.contains(this)) {
                actor.removeItemFromInventory(this);
            }
        }
    }

    @Override
    public boolean checkActive() {
        return this.timeLeft > 0 && this.active;
    }

    @Override
    public void recurringEffect(Ground ground) {
        JumpableGroundManager jumpableGroundManager = JumpableGroundManager.getInstance();
        if (jumpableGroundManager.isJumpableGround(ground)) {
            JumpableGround jumpableGroundEquivalent = jumpableGroundManager.getJumpableGround(ground);
            jumpableGroundEquivalent.toggleJumpable();
        }
    }

    @Override
    public void recurringEffect(Actor actor, GameMap map, Display display) {
        EnemyManager enemyManager = EnemyManager.getInstance();
        Weapon currentWeapon = actor.getWeapon();
        if (enemyManager.isEnemy(actor)) {
            WeaponItem uselessWeapon = new WeaponClone(currentWeapon, NO_DAMAGE);
            this.addItemToFrontInventory(actor, uselessWeapon);
        } else if (actor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            // TODO: extract as constant
            if(display!=null) display.println("MARIO IS INVINCIBLE");
            System.out.println("TIME LEFT: " + this.timeLeft);
            this.decrementTime();
            WeaponItem allPowerfulWeapon = new WeaponClone(currentWeapon, MAX_DAMAGE);
            this.addItemToFrontInventory(actor, allPowerfulWeapon);
            Location playerLocation = map.locationOf(actor);
            if (JumpableGroundManager.getInstance().isJumpableGround(playerLocation.getGround())) {
                playerLocation.setGround(new Dirt());
                playerLocation.addItem(new Coin(UNEARTHED_COIN_VALUE));
            }
        }
    }


    private void addItemToFrontInventory(Actor actor, Item itemToAdd) {
        List<Item> tempList = new ArrayList<>(actor.getInventory());
        for (Item actorItem : tempList) {
            actor.removeItemFromInventory(actorItem);
        }
        Collections.reverse(tempList);
        tempList.add(itemToAdd);
        for (Item reverseOrderItem : tempList) {
            actor.addItemToInventory(reverseOrderItem);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " - " + this.timeLeft + " turns remaining";
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    private void decrementTime() {
        this.timeLeft -= 1;
    }

    public void toggleActive() {
        this.active = !this.active;
    }
}
