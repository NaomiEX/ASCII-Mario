package game;

import engine.positions.Location;


public interface Growable {

//    default void grow(Location location) {
//        this.setAge(this.getAge() + 1);
//        this.growEffect(location);
//    }

    void grow(Location location);

    int getAge();

    void setAge(int newAge);
}
