package web.api.application.controller.administration;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;
import web.api.application.service.article_draft.DreamBookArticleDraftService;
import web.api.application.service.article_draft.NewsArticleDraftService;
import web.api.application.service.article_draft.WomanArticleDraftService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by oleht on 12.01.2019
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/administration")
public class AdministrationController {

    private WomanArticleDraftService womanArticleDraftService;
    private NewsArticleDraftService newsArticleDraftService;
    private DreamBookArticleDraftService dreamBookArticleDraftService;

    public AdministrationController(WomanArticleDraftService womanArticleDraftService, NewsArticleDraftService newsArticleDraftService, DreamBookArticleDraftService dreamBookArticleDraftService) {
        this.womanArticleDraftService = womanArticleDraftService;
        this.newsArticleDraftService = newsArticleDraftService;
        this.dreamBookArticleDraftService = dreamBookArticleDraftService;
    }

    @PostMapping(path = "/woman/draft", consumes = {"multipart/form-data", "application/json"})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ArticleDraftDto addWomanArticle(@RequestPart("article") @Valid ArticleDraftDto article,
                                           @RequestPart("images") @Valid @NotNull @NotBlank MultipartFile[] images) throws IOException {
        ArticleDraftDto saved = womanArticleDraftService.save(article, images);
        return saved;
    }

    @RequestMapping(value = "/news/draft", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ArticleDraftDto upload(@RequestPart("article") @Valid ArticleDraftDto article,
                                  @RequestPart("images") @Valid @NotNull @NotBlank MultipartFile[] images) throws IOException {
        ArticleDraftDto saved = newsArticleDraftService.save(article, images);
        return saved;
    }

    @PostMapping(path = "/dreambook/draft", consumes = {"multipart/form-data", "application/json"})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ArticleDraftDto addDreamBookArticle(@RequestPart("article") @Valid ArticleDraftDto article,
                                               @RequestPart("images") @Valid @NotNull @NotBlank MultipartFile[] images) throws IOException {
        ArticleDraftDto saved = dreamBookArticleDraftService.save(article, images);
        return saved;
    }

}
