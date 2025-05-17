package game.npcs.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.Utils;
import game.actions.AttackAction;

import java.util.ArrayList;

public class AttackBehaviour implements Behaviour {

    // TODO: develop and use it to attack the player automatically.
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<>();
        for (Exit exit: map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            Actor destinationActor = destination.getActor();
            if (destinationActor != null && destinationActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
                actions.add(new AttackAction(destinationActor, exit.getName()));
            }
        }
        return !actions.isEmpty() ? actions.get(Utils.randIndex(actions)) : null;
    }
}
