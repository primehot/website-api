package web.api.service;

import web.api.dto.AbstractArticleCategoryDto;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.ArticleNavigationBarDto;
import web.api.dto.unit.PageableDto;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleService<D extends AbstractArticleCategoryDto> {

    Integer recommendedSize = 5;
    Integer recommendedFromDay = 7;
    Integer newestSize = 5;

    byte[] getArticleImage(Long articleId);

    D getById(Long id);

    D getMain();

    PageableDto<D> getPage(int page, int size);

    PageableDto<D> getTopicPage(int topicId, int page, int size);

    PageableDto<D> getHashTagPage(int hashTagId, int page, int size);

    ArticleNavigationBarDto getNavigationBarData();

    AdditionalArticlesDto getAdditionalArticles();
}
