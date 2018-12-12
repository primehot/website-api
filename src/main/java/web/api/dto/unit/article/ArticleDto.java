package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class ArticleDto extends AbstractArticleCategoryDto<Long> {
    private String title;
    private String hotContent;
    private String content;
    private String topic;
    private Long timesVisited;
}
