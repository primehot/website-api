package web.api.converter.news;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.ArticleCategory;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.util.HashTagUtil;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleEntityToDto implements Converter<NewsArticleEntity, ArticleDto> {

    @Synchronized
    @Nullable
    @Override
    public ArticleDto convert(NewsArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setTopic(NewsTopic.getById(entity.getNewsTopic()).getName());
        dto.setHotContent(entity.getHotContent());
        dto.setTimesVisited(entity.getTimesVisited());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(new ArticleCategoryDto(ArticleCategory.NEWS.getId(), ArticleCategory.NEWS.getName()));

        return dto;
    }
}
