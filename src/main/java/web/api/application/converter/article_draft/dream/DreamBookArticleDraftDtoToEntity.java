package web.api.application.converter.article_draft.dream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Synchronized;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.entity.article_draft.dream.DreamBookArticleDraftEntity;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;

/**
 * Created by oleht on 12.10.2018
 */
@Log
@Component
public class DreamBookArticleDraftDtoToEntity implements Converter<ArticleDraftDto, DreamBookArticleDraftEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public DreamBookArticleDraftEntity convert(ArticleDraftDto dto) {
        if (dto == null) {
            return null;
        }

        DreamBookArticleDraftEntity entity = new DreamBookArticleDraftEntity();
        entity.setTitle(dto.getTitle());
        try {
            entity.setContent(mapper.writeValueAsString(dto.getContent()));
        } catch (JsonProcessingException e) {
            log.severe("Cannot convert context to json.ArticleDraftDto id: " + dto.getId());
        }
        entity.setHotContent(dto.getHotContent());

        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
