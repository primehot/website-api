package web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.component.DreamBookNavigationBarDto;
import web.api.dto.component.DreamTitlePageDto;
import web.api.dto.unit.DreamBookDto;
import web.api.service.DreamBookService;

import java.util.List;

/**
 * Created by oleht on 20.11.2018
 */
@Controller
public class DreamBookController {

    private DreamBookService dreamBookService;

    public DreamBookController(DreamBookService dreamBookService) {
        this.dreamBookService = dreamBookService;
    }

    @GetMapping("/dream")
    @ResponseBody
    public List<DreamBookDto> getDataFTS(@RequestParam("phrase") String phrase) {
        return dreamBookService.getDreamBooksByPhrase(phrase);
    }

    @GetMapping("/dream/title")
    @ResponseBody
    public DreamTitlePageDto getDataByTitle(@RequestParam("name") String title) {
        return dreamBookService.getDataByTitle(title);
    }

    @GetMapping("/dream/navbar")
    @ResponseBody
    public DreamBookNavigationBarDto getNavBarData() {
        return dreamBookService.getNavigationBarData();
    }
}
