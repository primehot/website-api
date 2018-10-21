package web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.MainDto;
import web.api.service.MainArticleService;

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
    public MainDto getMainData() {
        return mainArticleService.getMainDto();
    }
}
