package web.api.application.dto.unit.article;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.unit.article_draft.ParagraphDto;

import java.util.List;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class ArticleDto extends AbstractArticleCategoryDto<Long> {
    private String title;
    private String hotContent;
    private List<ParagraphDto> content;
    private String topic;
    private Long timesVisited;

    private ShortArticleDto previous;
    private ShortArticleDto next;
}
