package web.api.application.converter.article_draft.woman;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.WomanTopic;
import web.api.application.domain.entity.article_draft.woman.WomanArticleDraftEntity;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class WomanArticleDraftDtoToEntity implements Converter<ArticleDraftDto, WomanArticleDraftEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Nullable
    @Override
    public WomanArticleDraftEntity convert(ArticleDraftDto dto) {
        if (dto == null) {
            return null;
        }

        WomanArticleDraftEntity entity = new WomanArticleDraftEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(mapper.convertValue(dto.getContent(), String.class));
        entity.setWomanTopic(WomanTopic.getById(dto.getTopic().getId()));
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));


        return entity;
    }
}
