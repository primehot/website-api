package web.api.controller;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.domain.arcticle.HashTag;
import web.api.dto.AbstractArticleCategoryDto;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.ShortArticleDto;
import web.api.dto.unit.TagDto;
import web.api.dto.unit.news.NewsArticleDto;
import web.api.dto.unit.woman.WomanArticleDto;
import web.api.service.ArticleService;
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

    @GetMapping("/tags/{id}")
    @ResponseBody
    public PageableDto getTagsPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pair<Integer, Integer> sizes = getSizes(size);
        PageableDto<NewsArticleDto> news = newsArticleService.getHashTagPage(id, page, sizes.getFirst());
        PageableDto<WomanArticleDto> woman = womanArticleService.getHashTagPage(id, page, sizes.getSecond());

        Collection<AbstractArticleCategoryDto> allItems = new ArrayList<>();
        allItems.addAll(news.getItems());
        allItems.addAll(woman.getItems());

        return new PageableDto<>(allItems, news.getTotalPages() + woman.getTotalPages(), news.getTotalElements() + woman.getTotalElements());
    }

    @GetMapping("/tags/{id}/name")
    @ResponseBody
    public TagDto getTagName(@PathVariable("id") int id) {
        HashTag hashTag = HashTag.getById(id);
        return new TagDto(hashTag.getId(), hashTag.toString(), hashTag.getName());
    }

    @GetMapping("/tags/additional")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData() {
        AdditionalArticlesDto newAdditional = newsArticleService.getAdditionalArticles();
        AdditionalArticlesDto womanAdditional = womanArticleService.getAdditionalArticles();

        Collection<ShortArticleDto> recommended = new ArrayList<>();
        for (int i = 0; i < ArticleService.recommendedSize; i++) {
            if (i % 2 == 0) {
                ShortArticleDto item = (ShortArticleDto)newAdditional.getRecommended().iterator().next();
                recommended.add(item);
            } else {
                ShortArticleDto item = (ShortArticleDto)womanAdditional.getRecommended().iterator().next();
                recommended.add(item);
            }
        }

        Collection<ShortArticleDto> newest = new ArrayList<>();
        for (int i = 0; i < ArticleService.newestSize; i++) {
            if (i % 2 == 0) {
                ShortArticleDto item = (ShortArticleDto)newAdditional.getNewest().iterator().next();
                newest.add(item);
            } else {
                ShortArticleDto item = (ShortArticleDto)womanAdditional.getNewest().iterator().next();
                newest.add(item);
            }
        }

        AdditionalArticlesDto<ShortArticleDto> tagsAdditional = new AdditionalArticlesDto<>();
        tagsAdditional.setRecommended(recommended);
        tagsAdditional.setNewest(newest);

        return tagsAdditional;

    }

    private Pair<Integer, Integer> getSizes(Integer wholeSize) {
        int newsSize;
        int womanSize;
        if (wholeSize % 2 == 0) {
            newsSize = womanSize = wholeSize / 2;
        } else {
            newsSize = wholeSize / 2;
            womanSize = wholeSize / 2 + 1;
        }

        return Pair.of(newsSize, womanSize);
    }

}
