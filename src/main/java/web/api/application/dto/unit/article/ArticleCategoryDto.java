package web.api.application.dto.unit.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.api.application.domain.ArticleCategory;
import web.api.application.dto.AbstractDto;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ArticleCategoryDto extends AbstractDto<Integer> {
    private String name;

    private ArticleCategoryDto(Integer id, String name) {
        this.setId(id);
        this.name = name;
    }

    public static ArticleCategoryDto getArticleCategoryDto(ArticleCategory category) {
        return new ArticleCategoryDto(category.getId(), category.getName());
    }

    public static ArticleCategoryDto getNewsCategory() {
        return new ArticleCategoryDto(ArticleCategory.NEWS.getId(), ArticleCategory.NEWS.getName());
    }

    public static ArticleCategoryDto getWomanCategory() {
        return new ArticleCategoryDto(ArticleCategory.WOMEN.getId(), ArticleCategory.WOMEN.getName());
    }

    public static ArticleCategoryDto getDreamBookCategory() {
        return new ArticleCategoryDto(ArticleCategory.DREAMBOOK.getId(), ArticleCategory.DREAMBOOK.getName());
    }

    public static ArticleCategoryDto getDreamCategory() {
        return new ArticleCategoryDto(ArticleCategory.DREAM.getId(), ArticleCategory.DREAM.getName());
    }

}
