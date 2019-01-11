package web.api.application.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.application.domain.arcticle.news.NewsTopic;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.component.ArticleNavigationBarDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.domain.arcticle.woman.WomanTopic;
import web.api.application.dto.unit.TopicDto;
import web.api.application.service.article.WomanArticleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
@RequestMapping("women")
public class WomanArticleController {

    private WomanArticleService womanArticleService;

    public WomanArticleController(WomanArticleService womanArticleService) {
        this.womanArticleService = womanArticleService;
    }

    @GetMapping
    @ResponseBody
    public PageableDto getWomanPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return womanArticleService.getPage(page, size);
    }

    @GetMapping("/by-topics/{id}")
    @ResponseBody
    public PageableDto getWomanTopicPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return womanArticleService.getTopicPage(id, page, size);
    }

    @GetMapping("/topics/{id}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable("id") int id) {
        return TopicDto.of(WomanTopic.getById(id));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ArticleDto getWomanArticleById(@PathVariable("id") long id) {
        return womanArticleService.getById(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getWomanArticleImageById(@PathVariable("id") long id) {
        byte[] image = womanArticleService.getMainImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/topics")
    @ResponseBody
    public List<TopicDto> getTopic() {
        return Arrays.stream(WomanTopic.values()).map(TopicDto::of).collect(Collectors.toList());
    }

    @GetMapping("/navbar")
    @ResponseBody
    public ArticleNavigationBarDto getNavBarData() {
        return womanArticleService.getNavigationBarData();
    }

    @GetMapping("/additional")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData() {
        return womanArticleService.getAdditionalArticles();
    }

    @GetMapping("/additional/topic/{topicId}")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData(@PathVariable("topicId") Integer topicId) {
        return womanArticleService.getAdditionalArticlesByTopic(topicId);
    }
}
