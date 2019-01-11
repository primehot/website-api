package web.api.application.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.service.DreamBookService;
import web.api.application.service.article.DreamBookArticleService;

/**
 * Created by oleht on 20.11.2018
 */
@Controller
@RequestMapping("dreambook")
public class DreamBookArticleController {

    private DreamBookService dreamBookService;
    private DreamBookArticleService dreamBookArticleService;

    public DreamBookArticleController(DreamBookService dreamBookService, DreamBookArticleService dreamBookArticleService) {
        this.dreamBookService = dreamBookService;
        this.dreamBookArticleService = dreamBookArticleService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ArticleDto getNewsById(@PathVariable("id") long id) {
        return dreamBookArticleService.getById(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getNewsArticleImageById(@PathVariable("id") long id) {
        byte[] image = dreamBookArticleService.getMainImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/additional")
    @ResponseBody
    public AdditionalArticlesDto getAdditionalData() {
        return dreamBookArticleService.getAdditionalArticles();
    }
}
