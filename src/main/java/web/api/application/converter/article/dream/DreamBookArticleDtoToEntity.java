package web.api.application.converter.article.dream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Synchronized;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.entity.arcticle.dream.DreamBookArticleEntity;

/**
 * Created by oleht on 12.10.2018
 */
@Log
@Component
public class DreamBookArticleDtoToEntity implements Converter<DreamBookArticleDto, DreamBookArticleEntity> {

    @Autowired
    private ObjectMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public DreamBookArticleEntity convert(DreamBookArticleDto dto) {
        if (dto == null) {
            return null;
        }

        DreamBookArticleEntity entity = new DreamBookArticleEntity();
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
