package web.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by oleht on 19.10.2018
 */
@Getter
@Setter
public class NavigationBarDto <A extends AbstractArticleDto, S extends ShortArticleDto> {
    List<TopicDto> topics;
    List<A> articles;
    List<S> seeAlso;
}
