package web.api.application.converter.article.woman;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.application.domain.view.WomanArticleViewEntity;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ShortArticleDto;

import static web.api.application.util.ArticleUtil.cutShortContent;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class WomanArticleViewEntityToShortDto implements Converter<WomanArticleViewEntity, ShortArticleDto> {

    @Nullable
    @Override
    public ShortArticleDto convert(WomanArticleViewEntity entity) {
        if (entity == null) {
            return null;
        }

        ShortArticleDto dto = new ShortArticleDto();
        dto.setId(entity.getWomanId());
        dto.setTitle(entity.getTitle());
        dto.setShortContent(cutShortContent(entity.getHotContent()));
        dto.setArticleCategory(ArticleCategoryDto.getWomanCategory());

        return dto;
    }
}
