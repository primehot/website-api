package web.api.dto.woman;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
public class WomanArticleDto extends AbstractArticleDto<Long> {
    private String title;
    private String topic;
}
