package web.api.application.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.unit.article.AbstractArticleCategoryDto;

/**
 * Created by oleht on 23.01.2019
 */
@Getter
@Setter
public class NavigationArticleDto extends AbstractArticleCategoryDto<Long> {

    private String title;
    private String content;

}
