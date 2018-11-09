package web.api.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.dream.DreamBookArticleEntity;
import web.api.dto.unit.dream.DreamBookArticleDto;


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

        return dto;
    }
}
