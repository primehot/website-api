package web.api.dto.dream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.api.domain.arcticle.AbstractArticleEntity;
import web.api.dto.AbstractArticleDto;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
public class DreamBookArticleDto extends AbstractArticleDto<Long> {
    private String title;
}
