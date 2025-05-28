//package game.powerup;
//
//import engine.actions.Action;
//import engine.actors.Actor;
//import engine.displays.Display;
//import engine.items.PickUpItemAction;
//import engine.positions.GameMap;
//import engine.positions.Location;
//import game.reset.Resettable;
//import game.Status;
//import game.actions.ConsumeItemAction;
//import game.ground.highground.HighGround;
//import game.ground.highground.HighGroundManager;
//import game.items.Consumable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SuperMushroomOld extends PowerupItem implements Consumable, Resettable {
//    public static final Character DISPLAY_CHAR = '^';
//    public static final String NAME = "Super Mushroom";
//    public static final boolean PORTABLE = true;
//    private static final int MAX_HP_INCREASE = 50;
//    private final List<HighGround> unaffectedGround = new ArrayList<>();
//    /***
//     * Constructor.
//     */
//    public SuperMushroomOld() {
//        super(NAME, DISPLAY_CHAR, PORTABLE);
//        this.addAction(new ConsumeItemAction(this));
//    }
//
//
//    @Override
//    public List<Action> getAllowableActions() {
//        List<Action> actionList = new ArrayList<>();
//        actionList.add(new ConsumeItemAction(this));
//        return actionList;
//    }
//
//    @Override
//    public PickUpItemAction getPickUpAction(Actor actor) {
//        return null;
//    }
//
//    @Override
//    public void consumeEffect(Actor actor, GameMap map) {
////        this.affectedActor = actor;
//        this.setAffectedActor(actor);
//        EffectManager.getInstance().addEffect(actor, this);
//        // immediate effects
//        actor.increaseMaxHp(MAX_HP_INCREASE);
//        actor.addCapability(Status.TALL);
////        this.effect(actor, map);
//    }
//
//    @Override
//    public boolean checkActive() {
//        return !this.getAffectedActor().hasCapability(Status.RECENTLY_HURT);
//    }
//
//    public void effect(Actor actor, GameMap map, Display display) {
//        this.resetInstance(map.locationOf(actor));
////        actor.increaseMaxHp(MAX_HP_INCREASE);
////        actor.addCapability(Status.TALL);
//        this.alwaysSuceedJump();
//    }
//
//    private void alwaysSuceedJump() {
//        // since List keeps the order, no need to store mapping between old-new
//        for(HighGround highGround : HighGroundManager.getInstance().getAllModifiableHighGround()) {
//            this.unaffectedGround.add(new HighGround(highGround));
//            highGround.setSuccessRate(1);
//            highGround.setFallDamage(0);
//        }
//    }
//
//    private void immediateReset() {
//        List<HighGround> modifiedHighGround = HighGroundManager.getInstance().getAllModifiableHighGround();
//        if (this.unaffectedGround.size() == modifiedHighGround.size()) {
//            for(int i = 0 ; i < modifiedHighGround.size(); i++) {
//                HighGround original = unaffectedGround.get(i);
//                HighGround modified = modifiedHighGround.get(i);
//                modified.setSuccessRate(original.getSuccessRate());
//                modified.setFallDamage(original.getFallDamage());
//                //clean up
////                HighGroundManager.getInstance().removeHighGround(original);
//            }
//        }
//    }
//
//    @Override
//    public void resetInstance(Location location) {
//        if (!this.checkActive()) {
//            this.immediateReset();
//           this.getAffectedActor().removeCapability(Status.TALL);
//           EffectManager.getInstance().removeEffect(this.getAffectedActor(), this);
//        }
//    }
//}
