package github.AlpsBTE_Navigator.utils;

public class Utils {

    // Player Messages
    private static final String messagePrefix = "§7§l>> ";

    public static String getInfoMessageFormat(String info) {
        return messagePrefix + "§a" + info;
    }

    public static String getErrorMessageFormat(String error) {
        return messagePrefix + "§c" + error;
    }

    // Servers
    public final static String PLOT_SERVER = "ALPS-1";

    public final static String TERRA_SERVER = "ALPS-2";

    public final static String EVENT_SERVER = "ALPS-3";

    public final static String TEST_SERVER = "ALPS-4";
}
