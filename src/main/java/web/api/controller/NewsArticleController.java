package web.api.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.NavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.TopicDto;
import web.api.dto.unit.news.NewsArticleDto;
import web.api.service.NewsArticleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
public class NewsArticleController {

    private NewsArticleService newsArticleService;

    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    @GetMapping("/news")
    @ResponseBody
    public PageableDto getNewsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return newsArticleService.getPage(page, size);
    }

    @GetMapping("/news/by-topics/{id}")
    @ResponseBody
    public PageableDto getNewsTopicPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return newsArticleService.getTopicPage(id, page, size);
    }

    @GetMapping("/news/topics/{id}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable("id") int id) {
        NewsTopic topic = NewsTopic.getById(id);
        return new TopicDto(topic.getId(), topic.toString(), topic.getName());
    }

    @GetMapping("/news/main")
    @ResponseBody
    public NewsArticleDto getNewsMain() {
        return newsArticleService.getMain();
    }

    @GetMapping("/news/{id}")
    @ResponseBody
    public NewsArticleDto getNewsById(@PathVariable("id") long id) {
        return newsArticleService.getById(id);
    }

    @GetMapping("/news/{id}/image")
    public ResponseEntity<byte[]> getNewsArticleImageById(@PathVariable("id") long id) {
        byte[] image = newsArticleService.getArticleImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/news/topics")
    @ResponseBody
    public List<TopicDto> getTopic() {
        return Arrays.stream(NewsTopic.values()).map(e -> new TopicDto(e.getId(), e.toString(), e.getName())).collect(Collectors.toList());
    }

    @GetMapping("/news/navbar")
    @ResponseBody
    public NavigationBarDto getNavBarData() {
        return newsArticleService.getNavigationBarData();
    }

    @GetMapping("/news/additional")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData() {
        return newsArticleService.getAdditionalArticles();
    }
}
