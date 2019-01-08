package web.api.converter.dream;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.view.DreamBookArticleViewEntity;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ShortArticleDto;

import static web.api.util.ArticleUtil.cutShortContent;


/**
 * Created by oleht on 12.10.2018
 */
@Component
public class DreamBookArticleViewEntityToShortDto implements Converter<DreamBookArticleViewEntity, ShortArticleDto> {

    @Synchronized
    @Nullable
    @Override
    public ShortArticleDto convert(DreamBookArticleViewEntity entity) {
        if (entity == null) {
            return null;
        }

        ShortArticleDto dto = new ShortArticleDto();
        dto.setId(entity.getDreamBookId());
        dto.setTitle(entity.getTitle());
        dto.setShortContent(cutShortContent(entity.getHotContent()));
        dto.setArticleCategory(ArticleCategoryDto.getDreamBookCategory());

        return dto;
    }
}
