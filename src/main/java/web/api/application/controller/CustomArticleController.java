package web.api.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.application.domain.arcticle.HashTag;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.HashTagDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.TagDto;
import web.api.application.service.CustomService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oleht on 13.11.2018
 */
@Controller
public class CustomArticleController {

    private CustomService customService;

    public CustomArticleController(CustomService customService) {
        this.customService = customService;
    }

    @GetMapping("/tags/additional")
    @ResponseBody
    public AdditionalArticlesDto getTagAdditionalData() {
        return customService.getAdditionalData();
    }

    @GetMapping("/tags/{tagId}/additional")
    @ResponseBody
    public AdditionalArticlesDto getSearchAdditionalData(@PathVariable("tagId") int tagId) {
        return customService.getAdditionalArticlesByTag(tagId);
    }

    @GetMapping("/tags/{id}")
    @ResponseBody
    public PageableDto getArticlePageByTag(@PathVariable("id") int id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return customService.getTagsPage(id, page, size);
    }

    @GetMapping("/tags")
    @ResponseBody
    public List<HashTagDto> getTags() {
        return Arrays.stream(HashTag.values()).map(h -> new HashTagDto(h.getId(), h.getName())).collect(Collectors.toList());
    }

    @GetMapping("/tags/{id}/name")
    @ResponseBody
    public TagDto getTagName(@PathVariable("id") int id) {
        return TagDto.of(HashTag.getById(id));
    }

    @GetMapping("/search")
    @ResponseBody
    public PageableDto getArticlePageByPhrase(@RequestParam("phrase") String phrase, @RequestParam("page") int page, @RequestParam("size") int size) {
        return customService.getByPhrase(phrase, page, size);
    }

    @GetMapping("/search/additional")
    @ResponseBody
    public AdditionalArticlesDto getSearchAdditionalData() {
        return customService.getAdditionalData();
    }
}
