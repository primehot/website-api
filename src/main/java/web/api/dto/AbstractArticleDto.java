package web.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class AbstractArticleDto<T extends Number> extends AbstractDto<T> {
    private String hotContent;
    private String content;
    private Long timesVisited;
}
