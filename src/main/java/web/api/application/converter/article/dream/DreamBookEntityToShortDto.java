package web.api.application.converter.article.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.entity.dream_book.DreamBookEntity;
import web.api.application.dto.unit.ShortDreamBookDto;


/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookEntityToShortDto implements Converter<DreamBookEntity, ShortDreamBookDto> {

    @Synchronized
    @Nullable
    @Override
    public ShortDreamBookDto convert(DreamBookEntity entity) {
        if (entity == null) {
            return null;
        }

        ShortDreamBookDto dto = new ShortDreamBookDto();
        dto.setId(entity.getId());
        dto.setData(entity.getContent());

        return dto;
    }
}
