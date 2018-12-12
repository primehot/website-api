package web.api.service;

import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.article.ArticleDto;

/**
 * Created by oleht on 12.12.2018
 */
public interface CustomService {

    PageableDto<ArticleDto> getByPhrase(String phrase, int page, int size);

    PageableDto getTagsPage(int id, int page, int size);

    AdditionalArticlesDto getAdditionalData();

}
