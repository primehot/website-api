package web.api.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.article.ShortArticleDto;

import java.util.Collection;

/**
 * Created by oleht on 21.10.2018
 */
@Getter
@Setter
public class MainPageDto {
    private ShortArticleDto mainArticle;
    private Collection<ShortArticleDto> mainItems;
    private Collection<ShortArticleDto> recommendedNews;
    private Collection<ShortArticleDto> recommendedWomen;
}
