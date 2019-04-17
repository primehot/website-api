package web.api.application.service.article_draft;

import org.springframework.web.multipart.MultipartFile;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;

import java.io.IOException;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleDraftService {

    byte[] getMainImage(Long articleId);

    ArticleDraftDto getById(Long id);

    ArticleDraftDto save(ArticleDraftDto article, MultipartFile[] mainImage) throws IOException;

    PageableDto<ArticleDraftDto> getPage(int page, int size);

}
