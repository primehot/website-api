package web.api.application.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ShortArticleDto;

import java.util.List;

/**
 * Created by oleht on 19.10.2018
 */
@Getter
@Setter
public class ArticleNavigationBarDto<A extends ArticleDto, S extends ShortArticleDto> {
    List<TopicDto> topics;
    List<A> articles;
    List<S> seeAlso;
    List<S> mostCommented;
}
