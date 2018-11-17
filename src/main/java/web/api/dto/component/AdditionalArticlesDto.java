package web.api.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.ShortArticleDto;

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
