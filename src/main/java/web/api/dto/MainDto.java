package web.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by oleht on 21.10.2018
 */
@Getter
@Setter
public class MainDto {
    private ShortArticleDto mainArticle;
    private Collection<ShortArticleDto> mainItems;
    private Collection<ShortArticleDto> recommendedNews;
    private Collection<ShortArticleDto> recommendedWomen;
}
