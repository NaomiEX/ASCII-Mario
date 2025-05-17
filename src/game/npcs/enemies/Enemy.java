package game.npcs.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Priorities;
import game.reset.Resettable;
import game.Status;
import game.actions.AttackAction;
import game.npcs.behaviours.*;
import game.effects.EffectManager;

import java.util.*;

public abstract class Enemy extends Actor implements Resettable {
    private static final Behaviour DEFAULT_BEHAVIOUR = new WanderBehaviour();
    private final Map<Integer, Behaviour> behaviours = new TreeMap<>(); // priority, behaviour
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.registerInstance();
        this.behaviours.put(Priorities.LOW_PRIORITY, DEFAULT_BEHAVIOUR);
        EnemyManager.getInstance().addEnemy(this);
    }

//    public Enemy(String name, char displayChar, int hitPoints, Behaviour defaultBehaviour, int defaultBehaviourPriority) {
//        super(name, displayChar, hitPoints);
//        this.behaviours.put(defaultBehaviourPriority, defaultBehaviour);
//        this.registerInstance();
//
//    }

    /**
     * At the moment, we only make it can be attacked by Player.
     * You can do something else with this method.
     * @param otherActor the Actor that might perform an action.
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     * @see Status#HOSTILE_TO_ENEMY
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        EffectManager.getInstance().run(otherActor, this, map, null);
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this,direction));
        }
        EffectManager.getInstance().resetActorEffects(otherActor, map.locationOf(otherActor));


        return actions;
    }

    /**
     * Figure out what to do next.
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(Status.RESET)) {
            this.resetInstance(map.locationOf(this));
            return new DoNothingAction();
        }
        Actor destinationActor = this.getNearbyPlayer(map);
        if (destinationActor != null) {
            EffectManager.getInstance().run(destinationActor,this, map, display);
            this.addBehaviour(new AttackBehaviour(), Priorities.HIGH_PRIORITY);
            this.addBehaviour(new FollowBehaviour(destinationActor), Priorities.MEDIUM_PRIORITY);
        }
        Action chosenAction = new DoNothingAction();
        //TODO: printing only for debugging purposes
        if (destinationActor != null) display.println("Goomba behaviours: ");
        for(Behaviour behaviour : behaviours.values()) {
            if (destinationActor != null) display.println(behaviour.toString());
            Action action = behaviour.getAction(this, map);
            if (action != null) {
                chosenAction= action;
                break;
            }
        }
        if(destinationActor != null) {
            EffectManager.getInstance().resetActorEffects(destinationActor,map.locationOf(this));
        }

        return chosenAction;
    }

    private Actor getNearbyPlayer(GameMap map) {
        Location thisLocation = map.locationOf(this);
        for (Exit exit: thisLocation.getExits()) {
            Location exitDestination = exit.getDestination();
            Actor destinationActor = exitDestination.getActor();
            if (destinationActor != null &&
                    destinationActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                        return destinationActor;
            }
        }
        return null;
    }

    @Override
    public void resetInstance(Location location) {
        EnemyManager.getInstance().removeEnemy(this);
        location.map().removeActor(this);
    }

    public void addBehaviour(Behaviour newBehaviour, int behaviourPriority) {
        this.behaviours.put(behaviourPriority, newBehaviour);
    }

    public boolean hasBehaviour(Behaviour behaviour) {
        for(Behaviour enemyBehaviour : this.behaviours.values()) {
            if(enemyBehaviour.equals(behaviour)) {
                return true;
            }
        }
        return false;
    }

    protected final void clearBehaviours() {
        this.behaviours.clear();
    }
}
