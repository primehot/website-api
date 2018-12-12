package web.api.service.article;

import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.ArticleNavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleService {

    Integer recommendedSize = 5;
    Integer recommendedFromDay = 7;
    Integer newestSize = 5;

    byte[] getArticleImage(Long articleId);

    ArticleDto getById(Long id);

    ArticleDto getMain();

    PageableDto<ArticleDto> getPage(int page, int size);

    PageableDto<ArticleDto> getTopicPage(int topicId, int page, int size);

    PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size);

    ArticleNavigationBarDto getNavigationBarData();

    AdditionalArticlesDto getAdditionalArticles();
}
