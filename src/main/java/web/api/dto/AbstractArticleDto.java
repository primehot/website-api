package web.api.dto;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.arcticle.HashTag;
import web.api.dto.unit.ArticleCategoryDto;
import web.api.dto.unit.HashTagDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class AbstractArticleDto<T extends Number> extends AbstractArticleCategoryDto<T> {
    private String hotContent;
    private String content;
    private String topic;
    private Long timesVisited;
}
