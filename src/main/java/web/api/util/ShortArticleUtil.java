package web.api.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by oleht on 19.10.2018
 */
public class ShortArticleUtil {

    public static String cutArticleContent(String content) {
        return cutToShort(content, 120);
    }

    public static String cutShortContent(String content) {
        return cutToShort(content, 60);
    }

    private static String cutToShort(String content, int howLong) {
        if(content==null || content.isEmpty()) {
            return "";
        }
        String[] list = content.substring(0, howLong).split(" ");
        list[list.length - 1] = "...";
        return Arrays.stream(list).collect(Collectors.joining(" "));
    }

}
