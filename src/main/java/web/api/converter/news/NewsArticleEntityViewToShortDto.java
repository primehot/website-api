package web.api.converter.news;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.view.NewsArticleViewEntity;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ShortArticleDto;

import static web.api.util.ArticleUtil.cutShortContent;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleEntityViewToShortDto implements Converter<NewsArticleViewEntity, ShortArticleDto> {

    @Synchronized
    @Nullable
    @Override
    public ShortArticleDto convert(NewsArticleViewEntity entity) {
        if (entity == null) {
            return null;
        }

        ShortArticleDto dto = new ShortArticleDto();
        dto.setId(entity.getNewsId());
        dto.setTitle(entity.getTitle());
        dto.setShortContent(cutShortContent(entity.getHotContent()));
        dto.setArticleCategory(ArticleCategoryDto.getNewsCategory());

        return dto;
    }
}
