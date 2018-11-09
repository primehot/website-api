package web.api.controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.NavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.ShortArticleDto;
import web.api.dto.unit.TopicDto;
import web.api.dto.unit.woman.WomanArticleDto;
import web.api.service.WomanArticleService;

import java.util.Collection;
import java.util.List;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
public class WomanArticleController {

    private WomanArticleService womanArticleService;

    public WomanArticleController(WomanArticleService womanArticleService) {
        this.womanArticleService = womanArticleService;
    }

    @GetMapping("/women")
    @ResponseBody
    public PageableDto getWomanPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return womanArticleService.getPage(page, size);
    }

    @GetMapping("/women/by-topics/{id}")
    @ResponseBody
    public PageableDto getWomanTopicPage(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return womanArticleService.getTopicPage(id, page, size);
    }

    @GetMapping("/women/main")
    @ResponseBody
    public WomanArticleDto getWomanMainArticle() {
        return womanArticleService.getMain();
    }

    @GetMapping("/women/{id}")
    @ResponseBody
    public WomanArticleDto getWomanArticleById(@PathVariable("id") long id) {
        return womanArticleService.getById(id);
    }

    @GetMapping("/women/{id}/image")
    public ResponseEntity<byte[]> getWomanArticleImageById(@PathVariable("id") long id) {
        byte[] image = womanArticleService.getArticleImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/women/topics")
    @ResponseBody
    public List<TopicDto> getTopic() {
        return null;
    }

    @GetMapping("/women/navbar")
    @ResponseBody
    public NavigationBarDto getNavBarData() {
        return womanArticleService.getNavigationBarData();
    }

    @GetMapping("/women/additional")
    @ResponseBody
    public AdditionalArticlesDto getWomanRecommended() {
        return womanArticleService.getAdditionalArticles();
    }
}
