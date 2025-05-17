package game.npcs;

public class Monologue {
    public static final String KOOPA_DESTROY_TIP = "\"You might need a wrench to smash Koopa's hard shells.\"";
    public static final String POWER_STAR = "\"You better get back to finding the Power Stars.\"";
    public static final String PRINCESS_LORE = "\"The Princess is depending on you! You are our only hope.\"";
    public static final String TOAD_FUN_MESSAGE = "\"Being imprisoned in these walls can drive a fungus crazy :(\"";

    private static final String[] MONOLOGUE_OPTIONS = {
            KOOPA_DESTROY_TIP,
            POWER_STAR,
            PRINCESS_LORE,
            TOAD_FUN_MESSAGE
    };

    public static String[] getMonologueOptions() {
        return MONOLOGUE_OPTIONS;
    }
}
