package web.api.application.converter.article.news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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
@Log
@Component
public class NewsArticleDtoToEntity implements Converter<ArticleDto, NewsArticleEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Nullable
    @Override
    public NewsArticleEntity convert(ArticleDto dto) {
        if (dto == null) {
            return null;
        }

        NewsArticleEntity entity = new NewsArticleEntity();
        entity.setTitle(dto.getTitle());
        try {
            entity.setContent(mapper.writeValueAsString(dto.getContent()));
        } catch (JsonProcessingException e) {
            log.severe("Cannot convert context to json.ArticleDraftDto id: " + dto.getId());
        }
        entity.setNewsTopic(NewsTopic.getByName(dto.getTopic()));
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
