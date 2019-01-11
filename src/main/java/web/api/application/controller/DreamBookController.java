package web.api.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.application.dto.component.DreamBookNavigationBarDto;
import web.api.application.dto.component.DreamTitlePageDto;
import web.api.application.dto.unit.DreamBookDto;
import web.api.application.service.DreamBookService;
import web.api.application.service.article.DreamBookArticleService;

import java.util.List;

/**
 * Created by oleht on 20.11.2018
 */
@Controller
@RequestMapping("dream")
public class DreamBookController {

    private DreamBookService dreamBookService;
    private DreamBookArticleService dreamBookArticleService;

    public DreamBookController(DreamBookService dreamBookService, DreamBookArticleService dreamBookArticleService) {
        this.dreamBookService = dreamBookService;
        this.dreamBookArticleService = dreamBookArticleService;
    }

    @GetMapping
    @ResponseBody
    public List<DreamBookDto> getDataFTS(@RequestParam("phrase") String phrase) {
        return dreamBookService.getDreamBooksByPhrase(phrase);
    }

    @GetMapping("/title")
    @ResponseBody
    public DreamTitlePageDto getDataByTitle(@RequestParam("name") String title) {
        return dreamBookService.getDataByTitle(title);
    }

    @GetMapping("/navbar")
    @ResponseBody
    public DreamBookNavigationBarDto getNavBarData() {
        return dreamBookService.getNavigationBarData();
    }
}
