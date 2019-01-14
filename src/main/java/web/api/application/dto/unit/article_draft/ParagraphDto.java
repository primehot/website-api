package web.api.application.dto.unit.article_draft;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.AbstractArticleCategoryDto;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ParagraphDto {
    private String content;
    private String typeId;
}
