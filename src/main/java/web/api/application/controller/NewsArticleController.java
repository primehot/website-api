package web.api.application.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.component.ArticleNavigationBarDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.domain.arcticle.news.NewsTopic;
import web.api.application.dto.unit.TopicDto;
import web.api.application.service.article.NewsArticleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
@RequestMapping("news")
public class NewsArticleController {

    private NewsArticleService newsArticleService;

    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    @GetMapping
    @ResponseBody
    public PageableDto getNewsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return newsArticleService.getPage(page, size);
    }

    @GetMapping("/by-topics/{id}")
    @ResponseBody
    public PageableDto getNewsTopicPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return newsArticleService.getTopicPage(id, page, size);
    }

    @GetMapping("/topics/{id}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable("id") int id) {
        return TopicDto.of(NewsTopic.getById(id));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ArticleDto getNewsById(@PathVariable("id") long id) {
        return newsArticleService.getById(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getNewsArticleImageById(@PathVariable("id") long id) {
        byte[] image = newsArticleService.getMainImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/topics")
    @ResponseBody
    public List<TopicDto> getTopic() {
        return Arrays.stream(NewsTopic.values()).map(TopicDto::of).collect(Collectors.toList());
    }

    @GetMapping("/navbar")
    @ResponseBody
    public ArticleNavigationBarDto getNavBarData() {
        return newsArticleService.getNavigationBarData();
    }

    @GetMapping("/additional")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData() {
        return newsArticleService.getAdditionalArticles();
    }

    @GetMapping("/additional/topic/{topicId}")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData(@PathVariable("topicId") Integer topicId) {
        return newsArticleService.getAdditionalArticlesByTopic(topicId);
    }
}
