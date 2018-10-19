package web.api.service;

import web.api.domain.AbstractEntity;
import web.api.dto.AbstractDto;
import web.api.dto.NavigationBarDto;
import web.api.dto.PageableDto;

import java.util.Collection;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleService <E extends AbstractEntity, D extends AbstractDto>{

    byte[] getArticleImage(Long articleId);
    D getById(Long id);
    D getMain();
    PageableDto<D> getPage(int page, int size);
    PageableDto<D> getTopicPage(int topicId, int page, int size);

    NavigationBarDto getNavigationBarData();
    Collection<D> getRecommended();
}
