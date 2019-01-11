package web.api.application.util;

import org.springframework.data.util.Pair;

/**
 * Created by oleht on 12.12.2018
 */
public class PageUtil {

    public static Pair<Integer, Integer> getSizes(Integer wholeSize) {
        int newsSize;
        int womanSize;
        if (wholeSize % 2 == 0) {
            newsSize = womanSize = wholeSize / 2;
        } else {
            newsSize = wholeSize / 2;
            womanSize = wholeSize / 2 + 1;
        }

        return Pair.of(newsSize, womanSize);
    }
}
