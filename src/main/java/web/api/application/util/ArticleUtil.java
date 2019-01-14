package web.api.application.util;

import web.api.application.domain.entity.arcticle.AbstractArticleEntity;
import web.api.application.domain.ArticleCategory;
import web.api.application.domain.HashTag;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ShortArticleDto;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by oleht on 19.10.2018
 */
public class ArticleUtil {

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

    public static ShortArticleDto buildShortArticle(ArticleDto e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(), ArticleUtil.cutShortContent(e.getContent()), ArticleCategoryDto.getArticleCategoryDto(category), e.getHashTags());
    }

    public static ShortArticleDto buildShortArticle(AbstractArticleEntity e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(),
                ArticleUtil.cutShortContent(e.getContent()),
                ArticleCategoryDto.getArticleCategoryDto(category),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }

    public static ShortArticleDto buildNewest(AbstractArticleEntity e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(), e.getHotContent(), ArticleCategoryDto.getArticleCategoryDto(category),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }
}
