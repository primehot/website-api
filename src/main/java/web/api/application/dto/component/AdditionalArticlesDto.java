package web.api.application.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.unit.article.ShortArticleDto;

import java.util.Collection;

/**
 * Created by oleht on 09.11.2018
 */
@Getter
@Setter
public class AdditionalArticlesDto<S extends ShortArticleDto> {
    private Collection<S> recommended;
    private Collection<S> newest;
}
