package web.api.application.converter.article.woman;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.entity.arcticle.woman.WomanArticleEntity;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.domain.WomanTopic;
import web.api.application.dto.unit.article_draft.ParagraphDto;
import web.api.application.util.HashTagUtil;

import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Log
@Component
public class WomanArticleEntityToDto implements Converter<WomanArticleEntity, ArticleDto> {

    @Autowired
    private ObjectMapper mapper;

    @Nullable
    @Override
    public ArticleDto convert(WomanArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(mapper.convertValue(entity.getContent(), new TypeReference<List<ParagraphDto>>(){}));
        dto.setTopic(WomanTopic.getById(entity.getWomanTopic()).getName());
        dto.setHotContent(entity.getHotContent());
        dto.setTimesVisited(entity.getTimesVisited());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(ArticleCategoryDto.getWomanCategory());

        return dto;
    }
}
