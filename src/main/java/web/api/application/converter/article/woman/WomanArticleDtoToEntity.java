package web.api.application.converter.article.woman;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.HashTag;
import web.api.application.domain.entity.arcticle.woman.WomanArticleEntity;
import web.api.application.domain.WomanTopic;
import web.api.application.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class WomanArticleDtoToEntity implements Converter<ArticleDto, WomanArticleEntity> {

    @Nullable
    @Override
    public WomanArticleEntity convert(ArticleDto dto) {
        if (dto == null) {
            return null;
        }

        WomanArticleEntity entity = new WomanArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setWomanTopic(WomanTopic.getByName(dto.getTopic()));
        entity.setHotContent(dto.getHotContent());
        dto.getHashTags().forEach(ht -> entity.addHashTag(HashTag.getById(ht.getId())));

        return entity;
    }
}
