package web.api.application.service;

import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.12.2018
 */
public interface CustomService {

    PageableDto<ArticleDto> getByPhrase(String phrase, int page, int size);

    PageableDto getTagsPage(int id, int page, int size);

    AdditionalArticlesDto getAdditionalData();

    AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId);
}
