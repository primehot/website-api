package web.api.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.application.dto.component.MainPageDto;
import web.api.application.service.MainArticleService;

/**
 * Created by oleht on 21.10.2018
 */
@Controller
public class MainController {

    private MainArticleService mainArticleService;

    public MainController(MainArticleService mainArticleService) {
        this.mainArticleService = mainArticleService;
    }

    @GetMapping("/main")
    @ResponseBody
    public MainPageDto getMainData() {
        return mainArticleService.getMainDto();
    }

}
