package web.api.application.converter.article.dream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Synchronized;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.entity.arcticle.dream.DreamBookArticleEntity;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article_draft.ParagraphDto;
import web.api.application.util.HashTagUtil;

import java.util.List;


/**
 * Created by oleht on 12.10.2018
 */
@Log
@Component
public class DreamBookArticleEntityToDto implements Converter<DreamBookArticleEntity, ArticleDto> {

    @Autowired
    private ObjectMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public ArticleDto convert(DreamBookArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(mapper.convertValue(entity.getContent(), new TypeReference<List<ParagraphDto>>(){}));
        dto.setHotContent(entity.getHotContent());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(ArticleCategoryDto.getDreamBookCategory());

        return dto;
    }
}
