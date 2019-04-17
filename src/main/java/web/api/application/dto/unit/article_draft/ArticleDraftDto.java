package web.api.application.dto.unit.article_draft;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.AbstractArticleCategoryDto;

import java.util.List;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ArticleDraftDto extends AbstractArticleCategoryDto<Long> {
    private String title;
    private String hotContent;
    private List<ParagraphDto> content;
    private TopicDto topic;
    private String author;

}
