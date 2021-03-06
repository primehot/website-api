package web.api.application.service.article;

import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleService {

    byte[] getMainImage(Long articleId);

    ArticleDto getById(Long id);

    /**
     * We start from page 1 instead of 0. Because page 0 is reserved for recommended and newest data
     *
     * @param page
     * @param size
     * @return PageableDto<ArticleDraftDto>
     */
    PageableDto<ArticleDto> getPage(int page, int size);

    PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size);

    AdditionalArticlesDto getAdditionalArticles();

    AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId);

}
