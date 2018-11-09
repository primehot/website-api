package web.api.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.dream_book.DreamBookEntity;
import web.api.dto.unit.dream.DreamBookDto;


/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookDtoToEntity implements Converter<DreamBookEntity, DreamBookDto> {

    @Synchronized
    @Nullable
    @Override
    public DreamBookDto convert(DreamBookEntity entity) {
        if (entity == null) {
            return null;
        }

        DreamBookDto dto = new DreamBookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());

        return dto;
    }
}
