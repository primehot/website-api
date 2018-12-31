package web.api.util;

/**
 * Created by oleht on 09.12.2018
 */
public class FTSUtil {

    public static String or(String phrase) {
        return phrase.replaceAll(" ", "|");
    }

    public static String and(String phrase) {
        return phrase.replaceAll(" ", "&");
    }

    public static String not(String phrase) {
        return phrase.replaceAll(" ", "!");
    }

}
