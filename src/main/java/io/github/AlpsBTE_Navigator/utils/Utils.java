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

}
