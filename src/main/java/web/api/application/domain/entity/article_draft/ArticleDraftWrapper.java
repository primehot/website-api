package web.api.application.domain.entity.article_draft;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;

/**
 * Created by oleht on 14.01.2019
 */
@NoArgsConstructor
@Getter
@Setter
public class ArticleDraftWrapper {
    private MultipartFile mainImage;
    private ArticleDraftDto article;
}
