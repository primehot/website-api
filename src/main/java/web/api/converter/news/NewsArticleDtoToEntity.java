package web.api.converter.news;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.HashTag;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.dto.unit.article.ArticleDto;

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
