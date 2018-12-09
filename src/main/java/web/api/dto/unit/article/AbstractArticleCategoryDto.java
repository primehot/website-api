package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.arcticle.HashTag;
import web.api.dto.AbstractDto;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.HashTagDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
@Setter
public class AbstractArticleCategoryDto<I extends Number> extends AbstractDto<I> {
    private ArticleCategoryDto articleCategory;
    private List<HashTagDto> hashTags = new ArrayList<>();

    public void addHashTag(HashTagDto dto) {
        hashTags.add(dto);
    }

    public void addHashTag(Integer tagID) {
        HashTag tag = HashTag.getById(tagID);
        hashTags.add(new HashTagDto(tagID, tag.getName()));
    }
}