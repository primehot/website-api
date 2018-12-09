package web.api.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.article.AbstractArticleDto;
import web.api.dto.unit.article.ShortArticleDto;
import web.api.dto.unit.TopicDto;

import java.util.List;

/**
 * Created by oleht on 19.10.2018
 */
@Getter
@Setter
public class ArticleNavigationBarDto<A extends AbstractArticleDto, S extends ShortArticleDto> {
    List<TopicDto> topics;
    List<A> articles;
    List<S> seeAlso;
    List<S> mostCommented;
}