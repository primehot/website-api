package web.api.application.service.article;

import web.api.application.dto.component.ArticleNavigationBarDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.PageableDto;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleExtendedService extends ArticleService {

    /**
     * We start from page 1 instead of 0. Because page 0 is reserved for recommended and newest data
     *
     * @param page
     * @param size
     * @return PageableDto<ArticleDto>
     */
    PageableDto<ArticleDto> getTopicPage(int topicId, int page, int size);

    ArticleNavigationBarDto getNavigationBarData();

    AdditionalArticlesDto getAdditionalArticles();

    AdditionalArticlesDto getAdditionalArticlesByTopic(int topicId);

}
