package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class AbstractArticleDto<T extends Number> extends AbstractArticleCategoryDto<T> {
    private String hotContent;
    private String content;
    private String topic;
    private Long timesVisited;
}
