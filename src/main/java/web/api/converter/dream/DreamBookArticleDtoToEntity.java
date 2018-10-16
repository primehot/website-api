package web.api.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.dream.DreamBookArticleEntity;
import web.api.dto.dream.DreamBookArticleDto;

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

        return entity;
    }
}
