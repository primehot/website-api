package web.api.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.PageableDto;
import web.api.dto.woman.WomanArticleDto;
import web.api.service.WomanArticleService;

import java.util.Collection;

/**
 * Created by oleht on 14.10.2018
 */
@Controller
public class WomanArticleController {

    private WomanArticleService womanArticleService;

    public WomanArticleController(WomanArticleService womanArticleService) {
        this.womanArticleService = womanArticleService;
    }

    @GetMapping("/woman")
    @ResponseBody
    public PageableDto getWomanPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return womanArticleService.getPage(page, size);
    }

    @GetMapping("/woman/main")
    @ResponseBody
    public WomanArticleDto getWomanMainArticle() {
        return womanArticleService.getMain();
    }

    @GetMapping("/woman/{id}")
    @ResponseBody
    public WomanArticleDto getWomanArticleById(@PathVariable("page") long id) {
        return womanArticleService.getById(id);
    }

    @GetMapping("/woman/{id}/image")
    public ResponseEntity<byte[]> getWomanArticleImageById(@PathVariable("id") long id) {
        byte[] image = womanArticleService.getArticleImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/woman/topic/{id}")
    @ResponseBody
    public Collection<WomanArticleDto> getWomanByTopic(@PathVariable("page") int id) {
        return womanArticleService.getByTopic(id);
    }

    @GetMapping("/woman/recommended")
    @ResponseBody
    public Collection<WomanArticleDto> getWomanRecommended() {
        return womanArticleService.getRecommended();
    }
}
