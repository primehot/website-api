package web.api.util;

/**
 * Created by oleht on 09.12.2018
 */
public class FTSUtil {

    public static String or(String phrase) {
        return phrase.replaceAll(" ", "|");
    }

}
