package web.api.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractArticleCategoryDto;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class ShortArticleDto<T extends Number> extends AbstractArticleCategoryDto<T> {
    private String shortContent;

    public ShortArticleDto(T id, String shortContent, ArticleCategoryDto articleCategory) {
        this.shortContent = shortContent;
        this.setArticleCategory(articleCategory);
        this.setId(id);
    }
}
