package web.api.application.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.dream_book.DreamBookEntity;
import web.api.application.dto.unit.article.DreamBookArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookDtoToEntity implements Converter<DreamBookArticleDto, DreamBookEntity> {

    @Synchronized
    @Nullable
    @Override
    public DreamBookEntity convert(DreamBookArticleDto dto) {
        if (dto == null) {
            return null;
        }

        DreamBookEntity entity = new DreamBookEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        return entity;
    }
}
