package web.api.application.util;

import web.api.application.domain.arcticle.AbstractArticleEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 17.11.2018
 */
public class HashTagUtil {

    public static final String hashTagSeparator = "-";
    public static final String hashTagWrapper = "#";

    public static String wrapHashTag(Integer id) {
        return hashTagWrapper + id + hashTagWrapper;
    }

    public static Integer unwrapHashTag(String wprapedTag) {
        return Integer.parseInt(wprapedTag.replaceAll(hashTagWrapper, ""));
    }

    public static List<Integer> getHashTags(AbstractArticleEntity e) {
        return getHashTags(e.getHashTags());
    }

    public static List<Integer> getHashTags(String tags) {
        return Arrays.stream(tags.split(hashTagSeparator)).map(HashTagUtil::unwrapHashTag).collect(Collectors.toList());
    }

}
