package web.api.application.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.arcticle.dream.DreamBookArticleEntity;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.DreamBookArticleDto;
import web.api.application.util.HashTagUtil;


/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookArticleEntityToDto implements Converter<DreamBookArticleEntity, DreamBookArticleDto> {

    @Synchronized
    @Nullable
    @Override
    public DreamBookArticleDto convert(DreamBookArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        DreamBookArticleDto dto = new DreamBookArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setHotContent(entity.getHotContent());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(ArticleCategoryDto.getDreamBookCategory());

        return dto;
    }
}
