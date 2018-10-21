package web.api.converter.news;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.dto.news.NewsArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Component
public class NewsArticleEntityToDto implements Converter<NewsArticleEntity, NewsArticleDto> {

    @Synchronized
    @Nullable
    @Override
    public NewsArticleDto convert(NewsArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        NewsArticleDto dto = new NewsArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setTopic(NewsTopic.getById(entity.getNewsTopic()).getName());
        dto.setHotContent(entity.getHotContent());
        dto.setTimesVisited(entity.getTimesVisited());

        return dto;
    }
}
