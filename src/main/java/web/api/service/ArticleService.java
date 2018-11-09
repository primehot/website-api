package web.api.service;

import web.api.dto.AbstractDto;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.NavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.ShortArticleDto;

import java.util.Collection;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleService<D extends AbstractDto> {

    Integer recommendedSize = 10;
    Integer recommendedFromDay = 7;
    Integer newestSize = 4;

    byte[] getArticleImage(Long articleId);

    D getById(Long id);

    D getMain();

    PageableDto<D> getPage(int page, int size);

    PageableDto<D> getTopicPage(int topicId, int page, int size);

    NavigationBarDto getNavigationBarData();

    AdditionalArticlesDto getAdditionalArticles();
}
