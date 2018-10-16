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
import web.api.dto.PageableDto;
import web.api.dto.TopicDto;
import web.api.dto.news.NewsArticleDto;
import web.api.service.NewsArticleService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
public class NewsController {

    private NewsArticleService newsArticleService;

    public NewsController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    @GetMapping("/news")
    @ResponseBody
    public PageableDto getNewsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return newsArticleService.getPage(page, size);
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

    @GetMapping("/news/topics/{id}")
    @ResponseBody
    public Collection<NewsArticleDto> getNewsByTopic(@PathVariable("id") int id) {
        return newsArticleService.getByTopic(id);
    }

    @GetMapping("/news/topics")
    @ResponseBody
    public ResponseEntity<List<TopicDto>> getTopic() {
        List<TopicDto> list = Arrays.stream(NewsTopic.values()).map(e -> new TopicDto(e.getId(), e.toString(), e.getName())).collect(Collectors.toList());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(list);
    }

    @GetMapping("/news/recommended")
    @ResponseBody
    public Collection<NewsArticleDto> getNewsRecommended() {
        return newsArticleService.getRecommended();
    }
}
