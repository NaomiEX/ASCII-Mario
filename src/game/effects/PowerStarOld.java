//package game.powerup;
//
//import engine.actions.Action;
//import engine.actors.Actor;
//import engine.displays.Display;
//import engine.items.DropItemAction;
//import engine.items.Item;
//import engine.items.PickUpItemAction;
//import engine.positions.GameMap;
//import engine.positions.Location;
//import engine.weapons.Weapon;
//import engine.weapons.WeaponItem;
//import game.actions.ConsumeItemAction;
//import game.ground.Dirt;
//import game.ground.highground.HighGround;
//import game.ground.highground.HighGroundManager;
//import game.items.currency.Coin;
//import game.items.Consumable;
//import game.npcs.enemies.Enemy;
//import game.npcs.enemies.EnemyManager;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
////TODO: make literals constant
//public class PowerStarOld extends PowerupItem implements Consumable {
//    private static final String NAME = "Power Star";
//    public static final Character DISPLAY_CHAR = '*';
//    private static final boolean PORTABLE = false;
//    private static final int INITIAL_TIME_LEFT = 10;
//    private static final int HEAL_AMOUNT = 200;
//    private static final int UNEARTHED_COIN_VALUE = 5;
//    private static final int USELESS_WEAPON_DAMAGE = 0;
//    private int timeLeft;
//    /**
//     * Constructor.
//     */
//    public PowerStarOld() {
//        super(NAME, DISPLAY_CHAR, PORTABLE);
//        this.timeLeft = INITIAL_TIME_LEFT;
//    }
//
//    // when it's in inventory
//    @Override
//    public void tick(Location currentLocation, Actor actor) {
//        super.tick(currentLocation, actor);
//        this.decrementTime();
//        this.despawn(actor);
//    }
//
//    // when it's on the ground
//    @Override
//    public void tick(Location currentLocation) {
//        super.tick(currentLocation);
//        this.decrementTime();
//        this.despawn(currentLocation);
//
//    }
//
//    @Override
//    public void consumeEffect(Actor actor, GameMap map) {
//        this.setAffectedActor(actor);
//        EffectManager.getInstance().addEffect(actor, this);
//        actor.heal(HEAL_AMOUNT);
//        this.setTimeLeft(INITIAL_TIME_LEFT);
//        this.invincible();
//        this.canWalkOnHighGround();
//        this.alwaysKillOnHit();
//    }
//
//    @Override
//    public boolean checkActive() {
//        return this.getTimeLeft() > 0;
//    }
//
//    @Override
//    public void effect(Actor actor, GameMap map, Display display) {
//        this.resetInstance(map.locationOf(actor));
//        System.out.println("TIME LEFT: " + this.timeLeft);
//        display.println("MARIO IS INVINCIBLE");
//        Location currentLocation = map.locationOf(actor);
//        if (HighGroundManager.getInstance().isHighGround(currentLocation.getGround())) {
//            currentLocation.setGround(new Dirt());
//            currentLocation.addItem(new Coin(UNEARTHED_COIN_VALUE));
//        }
//        this.canWalkOnHighGround();
//        this.invincible();
//        this.alwaysKillOnHit();
//        this.decrementTime();
//    }
//
//    private void canWalkOnHighGround() {
//        for(HighGround highGround : HighGroundManager.getInstance().getAllModifiableHighGround()) {
//            if (highGround.isJumpable()) highGround.toggleJumpable();
//        }
//    }
//
//    private void invincible() {
//        for (Enemy enemy : EnemyManager.getInstance().getAllEnemies()) {
//            Weapon currentWeapon = enemy.getWeapon();
//            WeaponItem uselessWeapon = new VariableWeapon(
//                    currentWeapon.toString(), USELESS_WEAPON_DAMAGE, currentWeapon.verb(), currentWeapon.chanceToHit());
//            this.addItemToFront(enemy, uselessWeapon);
//        }
//    }
//
//    private void alwaysKillOnHit() {
//        Weapon currentWeapon = this.getAffectedActor().getWeapon();
//        WeaponItem allPowerfulWeapon = new VariableWeapon(
//                currentWeapon.toString(), Integer.MAX_VALUE, currentWeapon.verb(), currentWeapon.chanceToHit());
//        this.addItemToFront(this.getAffectedActor(), allPowerfulWeapon);
//    }
//
//    private void addItemToFront(Actor actor, Item itemToAdd) {
//        List<Item> tempList = new ArrayList<>(actor.getInventory());
//        for (Item item : tempList) {
//            actor.removeItemFromInventory(item);
//        }
//        Collections.reverse(tempList);
//        tempList.add(itemToAdd);
//        for (Item reversedItem : tempList) {
//            actor.addItemToInventory(reversedItem);
//        }
//    }
//
//    @Override
//    public List<Action> getAllowableActions() {
//        List<Action> actionList = new ArrayList<>();
//        actionList.add(new ConsumeItemAction(this));
//        return actionList;
//    }
//
//    private static final class VariableWeapon extends WeaponItem {
//        // this character doesn't matter since it will never be dropped
//        // see getDropAction
//        private static final Character DEFAULT_DISPLAY_CHARACTER = '?';
//
//        @Override
//        public DropItemAction getDropAction(Actor actor) {
//            return null;
//        }
//
//        /**
//         * Constructor.
//         *
//         * @param name        name of the item
//         * @param damage      amount of damage this weapon does
//         * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
//         * @param hitRate     the probability/chance to hit the target.
//         */
//        public VariableWeapon(String name, int damage, String verb, int hitRate) {
//            super(name, DEFAULT_DISPLAY_CHARACTER, damage, verb, hitRate);
//        }
//    }
//
//    public void immediateReset() {
//        if (this.getAffectedActor() != null) {
//
//            // reverse ability to walk on high ground
//            for(HighGround highGround : HighGroundManager.getInstance().getAllModifiableHighGround()) {
//                if (!highGround.isJumpable()) highGround.toggleJumpable();
//            }
//            // reverse invincibility
//            for (Enemy enemy : EnemyManager.getInstance().getAllEnemies()) {
//                if (!enemy.getInventory().isEmpty()) {
//                    Item firstItem = enemy.getInventory().get(0);
//                    enemy.removeItemFromInventory(firstItem);
//                }
//            }
//            // reverse one hit KO
//            List<Item> playerInventory = this.getAffectedActor().getInventory();
//            if (!playerInventory.isEmpty()) {
//                Item firstItem = this.getAffectedActor().getInventory().get(0);
//                this.getAffectedActor().removeItemFromInventory(firstItem);
//            }
//        }
//    }
//
//    @Override
//    public PickUpItemAction getPickUpAction(Actor actor) {
//        return null;
//    }
//
//    public void despawn(Location location) {
//        if (!this.checkActive()) location.removeItem(this);
//    }
//
//    public void despawn(Actor actor) {
//        if (!this.checkActive()) {
//            List<Item> playerInventory = actor.getInventory();
//            if (playerInventory != null && playerInventory.contains(this)) {
//                actor.removeItemFromInventory(this);
//            }
//        }
//    }
//
//    @Override
//    public void resetInstance(Location location) {
//        if (!this.checkActive()) {
//            this.immediateReset();
//            EffectManager.getInstance().removeEffect(this.getAffectedActor(), this);
////            List<Item> playerInventory = this.getAffectedActor().getInventory();
////            if (playerInventory.contains(this)) this.getAffectedActor().removeItemFromInventory(this);
////            if (location.getItems().contains(this)) location.removeItem(this);
//            // otherwise just let the effect run out
//        }
//    }
//
//    //FIXME: Trade Action also shows turns remaining
//    @Override
//    public String toString() {
//        return super.toString() + " - " + this.getTimeLeft() + " turns remaining";
//    }
//
//
//    public int getTimeLeft() {
//        return timeLeft;
//    }
//
//    public void setTimeLeft(int timeLeft) {
//        this.timeLeft = timeLeft;
//    }
//
//    public void decrementTime() {
//        this.setTimeLeft(this.getTimeLeft() - 1);
//    }
//
//
//
//}
