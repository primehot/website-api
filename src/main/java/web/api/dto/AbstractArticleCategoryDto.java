package web.api.dto;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.ArticleCategoryDto;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
@Setter
public class AbstractArticleCategoryDto<I extends Number> extends AbstractDto<I> {
    private ArticleCategoryDto articleCategory;
}
