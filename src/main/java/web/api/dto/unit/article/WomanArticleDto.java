package web.api.dto.unit.article;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
public class WomanArticleDto extends AbstractArticleDto<Long> {
    private String title;
}
