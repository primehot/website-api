package web.api.application.dto.unit.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.api.application.dto.unit.HashTagDto;

import java.util.List;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ShortArticleDto<T extends Number> extends AbstractArticleCategoryDto<T> {
    private String title;
    private String shortContent;

    public ShortArticleDto(T id, String shortContent, ArticleCategoryDto articleCategory, List<HashTagDto> hashTags) {
        this.shortContent = shortContent;
        this.setArticleCategory(articleCategory);
        this.setId(id);
        this.setHashTags(hashTags);
    }

    public ShortArticleDto(T id, String shortContent, ArticleCategoryDto articleCategory) {
        this.shortContent = shortContent;
        this.setArticleCategory(articleCategory);
        this.setId(id);
    }
}
