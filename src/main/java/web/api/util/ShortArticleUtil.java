package web.api.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by oleht on 19.10.2018
 */
public class ShortArticleUtil {

    public static String cutToShort(String content) {
        String[] list = content.substring(0, 120).split(" ");
        list[list.length - 1] = "...";
        return Arrays.stream(list).collect(Collectors.joining(" "));
    }

}
