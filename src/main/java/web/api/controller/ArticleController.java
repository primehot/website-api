package web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.AbstractArticleCategoryDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.news.NewsArticleDto;
import web.api.dto.unit.woman.WomanArticleDto;
import web.api.service.NewsArticleService;
import web.api.service.WomanArticleService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by oleht on 13.11.2018
 */
@Controller
public class ArticleController {

    private NewsArticleService newsArticleService;
    private WomanArticleService womanArticleService;

    public ArticleController(NewsArticleService newsArticleService, WomanArticleService womanArticleService) {
        this.newsArticleService = newsArticleService;
        this.womanArticleService = womanArticleService;
    }

    @GetMapping("/tag/{id}")
    @ResponseBody
    public PageableDto getNewsTopicPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        int newsSize;
        int womanSize;
        if (size % 2 == 0) {
            newsSize = womanSize = size / 2;
        } else {
            newsSize = size / 2;
            womanSize = size / 2 + 1;
        }
        PageableDto<NewsArticleDto> news = newsArticleService.getHashTagPage(id, page, newsSize);
        PageableDto<WomanArticleDto> woman = womanArticleService.getHashTagPage(id, page, womanSize);

        Collection<AbstractArticleCategoryDto> allItems = new ArrayList<>();
        allItems.addAll(news.getItems());
        allItems.addAll(woman.getItems());

        return new PageableDto<>(allItems, news.getTotalPages() + woman.getTotalPages(), news.getTotalElements() + woman.getTotalElements());
    }
}
