package web.api.converter.woman;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.ArticleCategory;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.domain.arcticle.woman.WomanTopic;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.WomanArticleDto;
import web.api.util.HashTagUtil;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class WomanArticleEntityToDto implements Converter<WomanArticleEntity, WomanArticleDto> {

    @Nullable
    @Override
    public WomanArticleDto convert(WomanArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        WomanArticleDto dto = new WomanArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setTopic(WomanTopic.getById(entity.getWomanTopic()).getName());
        dto.setHotContent(entity.getHotContent());
        dto.setTimesVisited(entity.getTimesVisited());
        HashTagUtil.getHashTags(entity).forEach(dto::addHashTag);
        dto.setArticleCategory(new ArticleCategoryDto(ArticleCategory.WOMEN.getId(), ArticleCategory.WOMEN.getName()));

        return dto;
    }
}
