package game.items.currency;

import engine.actors.Actor;
import engine.items.Item;
import engine.positions.Location;
import game.reset.Resettable;
import game.Status;

public class Coin extends Item implements Resettable {
    private static final String NAME = "Coin";
    private static final Character DISPLAY_CHARACTER = '$';
    private static final boolean PORTABLE = true;
    private int value;

    public Coin(int value) {
        super(NAME, DISPLAY_CHARACTER, PORTABLE);
//        this.addCapability(Status.CURRENCY);
        this.value = value;
        WorldBank.getInstance().addCoin(this);
        this.registerInstance();
    }

    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.tick(currentLocation);
    }

    @Override
    public void tick(Location currentLocation) {
        if (this.hasCapability(Status.RESET)) {
            this.resetInstance(currentLocation);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    @Override
    public String toString() {
        return super.toString() + "(" +
                this.getDisplayChar() + value +
                ')';
    }

    public String getCoinType() {
        return super.toString();
    }

    @Override
    public void resetInstance(Location location) {
        location.removeItem(this);
    }
}
