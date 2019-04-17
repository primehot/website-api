package web.api.application.util;

import org.springframework.stereotype.Component;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;
import web.api.application.domain.ArticleCategory;
import web.api.application.domain.HashTag;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ShortArticleDto;
import web.api.application.dto.unit.article_draft.ParagraphDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 19.10.2018
 */
@Component
public class ArticleUtil {

    public String cutArticleContent(List<ParagraphDto> content) {
        return cutToShort(content, 120);
    }

    public String cutShortContent(List<ParagraphDto> content) {
        return cutToShort(content, 60);
    }

    private String cutToShort(List<ParagraphDto> content, int howLong) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        String[] list;
        if (content.get(0).getContent().length() > howLong) {
            list = content.get(0).getContent().substring(0, howLong).split(" ");
        } else {
            list = content.get(0).getContent().split(" ");
        }

        list[list.length - 1] = "...";
        return Arrays.stream(list).collect(Collectors.joining(" "));
    }

    public ShortArticleDto buildShortArticle(ArticleDto e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(), ArticleUtil.cutShortContent(e.getContent()), ArticleCategoryDto.getArticleCategoryDto(category), e.getHashTags());
    }

    public ShortArticleDto buildShortArticle(AbstractArticleEntity e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(),
                ArticleUtil.cutShortContent(e.getContent()),
                ArticleCategoryDto.getArticleCategoryDto(category),
                HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                        .collect(Collectors.toList()));
    }

    public ShortArticleDto buildNewest(ArticleDto e, ArticleCategory category) {
        return new ShortArticleDto<>(e.getId(), e.getHotContent(), ArticleCategoryDto.getArticleCategoryDto(category),
                e.getHashTags());
    }
}
