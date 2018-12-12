package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.arcticle.ArticleCategory;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
@Setter
public class ArticleCategoryDto extends AbstractDto<Integer> {
    private String name;

    private ArticleCategoryDto(Integer id, String name) {
        this.setId(id);
        this.name = name;
    }

    public static ArticleCategoryDto getNewsCategory() {
        return new ArticleCategoryDto(ArticleCategory.NEWS.getId(), ArticleCategory.NEWS.getName());
    }

    public static ArticleCategoryDto getWomanCategory() {
        return new ArticleCategoryDto(ArticleCategory.WOMEN.getId(), ArticleCategory.WOMEN.getName());
    }

    public static ArticleCategoryDto getDreamCategory() {
        return new ArticleCategoryDto(ArticleCategory.DREAM.getId(), ArticleCategory.DREAM.getName());
    }

}
