package game.effects;

import engine.actors.Actor;
import engine.displays.Display;
import engine.positions.GameMap;
import engine.positions.Ground;
import engine.positions.Location;

import java.util.*;

//TODO: Find a way to merge duplicate functions (add a parent interface EffectCapable?)
public class EffectManager {
    private final Map<Actor, AbstractSet<RecurringGroundEffectCapable>> groundEffects;
    private final Map<Actor, AbstractSet<RecurringActorEffectCapable>> actorEffects;

    private static EffectManager instance;

    private EffectManager() {
        this.groundEffects = new HashMap<>();
        this.actorEffects = new HashMap<>();
    }

    public static EffectManager getInstance() {
        if (instance == null){
            instance = new EffectManager();
        }
        return instance;
    }

    public void addGroundEffect(Actor actor, RecurringGroundEffectCapable recurringGroundEffectCapable) {
        if (this.groundEffects.containsKey(actor)) {
            this.groundEffects.get(actor).add(recurringGroundEffectCapable);
        } else {
            AbstractSet<RecurringGroundEffectCapable> groundEffects = new HashSet<>();
            groundEffects.add(recurringGroundEffectCapable);
            this.groundEffects.put(actor, groundEffects);
        }
    }

    public void addActorEffect(Actor actor, RecurringActorEffectCapable recurringActorEffectCapable) {
        if (this.actorEffects.containsKey(actor)) {
            this.actorEffects.get(actor).add(recurringActorEffectCapable);
        } else {
            AbstractSet<RecurringActorEffectCapable> actorEffects = new HashSet<>();
            actorEffects.add(recurringActorEffectCapable);
            this.actorEffects.put(actor, actorEffects);
        }
    }


//    public void addEffect(Actor actor, EffectCapable powerup) {
//        if (allEffects.containsKey(actor)) {
//            allEffects.get(actor).add(powerup);
//
//        } else {
//            AbstractSet<EffectCapable> newPowerup = new HashSet<>();
//            newPowerup.add(powerup);
//            allEffects.put(actor, newPowerup);
//
//        }
//    }

    public void forceReset(Actor actor, Location location) {
        if (this.getGroundEffects(actor) != null) {
            AbstractSet<RecurringGroundEffectCapable> groundEffectCapables = new HashSet<>(this.getGroundEffects(actor));
            for (RecurringGroundEffectCapable groundEffect : groundEffectCapables) {
                groundEffect.toggleActive();
                groundEffect.resetInstance(location);
            }
        }
        if (this.getActorEffects(actor) != null) {
            AbstractSet<RecurringActorEffectCapable> actorEffectCapables = new HashSet<>(this.getActorEffects(actor));
            for (RecurringActorEffectCapable actorEffectCapable : actorEffectCapables) {
                actorEffectCapable.toggleActive();
                actorEffectCapable.resetInstance(null);
            }
        }
    }

    public void resetActorEffects(Actor actor, Location location) {
        AbstractSet<RecurringActorEffectCapable> actorEffectCapables = this.getActorEffects(actor);
        if (actorEffectCapables != null) {
            for (RecurringActorEffectCapable actorEffectCapable : Collections.unmodifiableSet(actorEffectCapables)) {
                actorEffectCapable.resetInstance(location);
            }
        }
    }

    public void resetGroundEffects(Actor actor, Location location) {
        if (this.getGroundEffects(actor) != null) {
            AbstractSet<RecurringGroundEffectCapable> groundEffectCapables = new HashSet<>(this.getGroundEffects(actor));

            for (RecurringGroundEffectCapable groundEffectCapable : groundEffectCapables) {
                groundEffectCapable.resetInstance(location);
            }

        }
    }

    public AbstractSet<RecurringActorEffectCapable> getActorEffects(Actor actor) {
        return this.actorEffects.get(actor);
    }

    public AbstractSet<RecurringGroundEffectCapable> getGroundEffects(Actor actor) {
        return this.groundEffects.get(actor);
    }

    public void run(Actor actor, Ground ground) {
        AbstractSet<RecurringGroundEffectCapable> groundEffects = this.getGroundEffects(actor);
        if (groundEffects != null) {
            for (RecurringGroundEffectCapable recurringGroundEffectCapable : groundEffects) {
                recurringGroundEffectCapable.recurringEffect(ground);
            }

        }
    }

    public void run(Actor actor, Actor targetActor, GameMap map, Display display) {
        AbstractSet<RecurringActorEffectCapable> actorEffects = this.getActorEffects(actor);
        if (actorEffects != null) {
            for (RecurringActorEffectCapable recurringActorEffectCapable : actorEffects) {
                recurringActorEffectCapable.recurringEffect(targetActor, map, display);
            }
        }
    }

//
//    public void removeEffect(Actor actor, EffectCapable powerup) {
//        AbstractSet<EffectCapable> actorPowerups = this.allEffects.get(actor);
//        actorPowerups.remove(powerup);
//    }

    public void removeGroundEffect(Actor actor, RecurringGroundEffectCapable effect) {
        AbstractSet<RecurringGroundEffectCapable> groundEffects = this.groundEffects.get(actor);
        groundEffects.remove(effect);
    }

    public void removeActorEffect(Actor actor, RecurringActorEffectCapable effect) {
        AbstractSet<RecurringActorEffectCapable> actorEffects = this.actorEffects.get(actor);
        actorEffects.remove(effect);
    }


}
