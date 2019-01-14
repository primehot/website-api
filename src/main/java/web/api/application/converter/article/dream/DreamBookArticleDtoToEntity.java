package web.api.application.converter.article.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.entity.arcticle.dream.DreamBookArticleEntity;
import web.api.application.dto.unit.article.DreamBookArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookArticleDtoToEntity implements Converter<DreamBookArticleDto, DreamBookArticleEntity> {

    @Synchronized
    @Nullable
    @Override
    public DreamBookArticleEntity convert(DreamBookArticleDto dto) {
        if (dto == null) {
            return null;
        }

        DreamBookArticleEntity entity = new DreamBookArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
