package game.reset;

import game.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 * TODO: you may modify (add or remove) methods in this class if you think they are not necessary.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * A3: Think about how will you improve this implementation in the future assessment.
 * What could be the drawbacks of this implementation?
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private final List<Resettable> resettableList;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }

    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     */
    public void run(){
        for (Resettable resettable : this.resettableList) {
            resettable.addCapability(Status.RESET);
        }
        List<Resettable> resettables = new ArrayList<>(this.resettableList);
        for (Resettable resettable : resettables) {
            this.cleanUp(resettable);
        }
        System.out.println("AFTER RESET CONTENTS");
        for (Resettable resettable : this.resettableList) {
            System.out.println(resettable);
        }
    }

    /**
     * Add the Resettable instance to the list
     * FIXME: it does nothing, you need to implement it :)
     */
    public void appendResetInstance(Resettable reset){
        this.resettableList.add(reset);
    }


    /**
     * Remove a Resettable instance from the list
     * @param resettable resettable object
     * FIXME: it does nothing, you need to implement it :)
     *                   TODO: add checks
     */
    public void cleanUp(Resettable resettable){
        this.resettableList.remove(resettable);
    }

    public boolean hasInstance(Resettable instance) {
        for (Resettable resettable : this.resettableList) {
            if(resettable == instance) {
                return true;
            }
        }
        return false;
    }


}
