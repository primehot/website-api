package web.api.application.converter.article.news;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.NewsTopic;
import web.api.application.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleDtoToEntity implements Converter<ArticleDto, NewsArticleEntity> {

    @Nullable
    @Override
    public NewsArticleEntity convert(ArticleDto dto) {
        if (dto == null) {
            return null;
        }

        NewsArticleEntity entity = new NewsArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNewsTopic(NewsTopic.getByName(dto.getTopic()));
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
