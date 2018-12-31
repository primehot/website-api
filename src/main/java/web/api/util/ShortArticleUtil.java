package web.api.util;

import web.api.domain.arcticle.HashTag;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.dto.unit.article.ShortArticleDto;

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
        if (content == null || content.isEmpty()) {
            return "";
        }
        String[] list;
        if (content.length() > howLong) {
            list = content.substring(0, howLong).split(" ");
        } else {
            list = content.split(" ");
        }

        list[list.length - 1] = "...";
        return Arrays.stream(list).collect(Collectors.joining(" "));
    }

    public static ShortArticleDto buildShortArticle(ArticleDto e) {
        return new ShortArticleDto<>(e.getId(), ShortArticleUtil.cutShortContent(e.getContent()), ArticleCategoryDto.getNewsCategory(), e.getHashTags());
    }

    public static ShortArticleDto buildShortArticle(WomanArticleEntity e) {
        return new ShortArticleDto<>(e.getId(),
                ShortArticleUtil.cutShortContent(e.getContent()),
                ArticleCategoryDto.getWomanCategory(),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }

    public static ShortArticleDto buildShortArticle(NewsArticleEntity e) {
        return new ShortArticleDto<>(e.getId(),
                ShortArticleUtil.cutShortContent(e.getContent()),
                ArticleCategoryDto.getNewsCategory(),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }

    public static ShortArticleDto buildNewest(NewsArticleEntity e) {
        return new ShortArticleDto<>(e.getId(), e.getHotContent(), ArticleCategoryDto.getNewsCategory(),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }

    public static ShortArticleDto buildNewest(WomanArticleEntity e) {
        return new ShortArticleDto<>(e.getId(), e.getHotContent(), ArticleCategoryDto.getWomanCategory(),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }
}
