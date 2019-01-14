package web.api.application.converter.article_draft.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.entity.article_draft.news.NewsArticleDraftEntity;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleDraftDtoToEntity implements Converter<ArticleDraftDto, NewsArticleDraftEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Nullable
    @Override
    public NewsArticleDraftEntity convert(ArticleDraftDto dto) {
        if (dto == null) {
            return null;
        }

        NewsArticleDraftEntity entity = new NewsArticleDraftEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(mapper.convertValue(dto.getContent(), String.class));
        entity.setNewsTopic(NewsTopic.getById(dto.getTopic().getId()));
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
