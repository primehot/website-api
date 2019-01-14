package web.api.application.converter.article_draft.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.entity.article_draft.news.NewsArticleDraftEntity;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;
import web.api.application.dto.unit.article_draft.ParagraphDto;
import web.api.application.util.HashTagUtil;

import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleDraftEntityToDto implements Converter<NewsArticleDraftEntity, ArticleDraftDto> {

    @Autowired
    private ObjectMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public ArticleDraftDto convert(NewsArticleDraftEntity entity) {
        if (entity == null) {
            return null;
        }

        ArticleDraftDto dto = new ArticleDraftDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(mapper.convertValue(entity.getContent(), new TypeReference<List<ParagraphDto>>(){}));
        dto.setTopic(TopicDto.of(NewsTopic.getById(entity.getNewsTopic())));
        dto.setHotContent(entity.getHotContent());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(ArticleCategoryDto.getNewsCategory());

        return dto;
    }
}
