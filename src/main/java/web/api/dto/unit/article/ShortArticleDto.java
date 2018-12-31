package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.HashTagDto;

import java.util.List;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class ShortArticleDto<T extends Number> extends AbstractArticleCategoryDto<T> {
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
