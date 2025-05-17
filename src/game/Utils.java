package game;

import java.util.List;
import java.util.Random;

public class Utils {
    private static final Random GENERATOR = new Random();
    public static int randInt(int low, int high){
        // nextInt() will return an integer between 0 and high-low inclusive
        return (GENERATOR.nextInt(high-low) + low);
    }

    public static int randIndex(List<?> someList) {
        return GENERATOR.nextInt(someList.size());
    }

    public static double randDouble() {
        return (GENERATOR.nextDouble());
    }

    public static boolean performRandomEvent(double threshold) {
        boolean performEvent = false;
        double randDouble = Utils.randDouble();
        if(randDouble < threshold) {
            performEvent = true;
        }
        return performEvent;
    }

}
