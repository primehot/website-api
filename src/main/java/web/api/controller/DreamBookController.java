package web.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.api.dto.component.DreamBookNavigationBarDto;
import web.api.service.DreamBookService;

/**
 * Created by oleht on 20.11.2018
 */
@Controller
public class DreamBookController {

    private DreamBookService dreamBookService;

    public DreamBookController(DreamBookService dreamBookService) {
        this.dreamBookService = dreamBookService;
    }

    @GetMapping("/dream/navbar")
    @ResponseBody
    public DreamBookNavigationBarDto getNavBarData() {
        return dreamBookService.getNavigationBarData();
    }
}
