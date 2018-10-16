package web.api.converter.woman;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.domain.arcticle.woman.WomanTopic;
import web.api.dto.woman.WomanArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class WomanArticleDtoToEntity implements Converter<WomanArticleDto, WomanArticleEntity> {

    @Nullable
    @Override
    public WomanArticleEntity convert(WomanArticleDto dto) {
        if (dto == null) {
            return null;
        }

        WomanArticleEntity entity = new WomanArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setNewsTopic(WomanTopic.getByName(dto.getTopic()));
        entity.setHotContent(dto.getHotContent());

        return entity;
    }
}
