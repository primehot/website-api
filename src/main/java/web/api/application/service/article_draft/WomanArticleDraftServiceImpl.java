package web.api.application.service.article_draft;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.converter.article_draft.woman.WomanArticleDraftDtoToEntity;
import web.api.application.converter.article_draft.woman.WomanArticleDraftEntityToDto;
import web.api.application.domain.entity.article_draft.woman.WomanArticleDraftEntity;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article_draft.WomanArticleDraftRepository;
import web.api.application.util.ImageUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by oleht on 12.01.2019
 */
@Service
public class WomanArticleDraftServiceImpl implements WomanArticleDraftService {
    private WomanArticleDraftRepository draftRepository;
    private WomanArticleDraftDtoToEntity dtoToEntity;
    private WomanArticleDraftEntityToDto entityToDto;

    public WomanArticleDraftServiceImpl(WomanArticleDraftRepository draftRepository, WomanArticleDraftDtoToEntity dtoToEntity, WomanArticleDraftEntityToDto entityToDto) {
        this.draftRepository = draftRepository;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @Override
    public byte[] getMainImage(Long articleId) {
        Optional<ImageProjection> item = Optional.empty();
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + articleId);
    }

    @Override
    public ArticleDraftDto getById(Long id) {
        WomanArticleDraftEntity article = draftRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found WomanArticle with id: " + id));

        return entityToDto.convert(article);
    }

    @Override
    public ArticleDraftDto save(ArticleDraftDto article, MultipartFile[] mainImage) throws IOException {
        WomanArticleDraftEntity art = dtoToEntity.convert(article);
//        art.setMainImage(ImageUtil.convertBytes(mainImage.getBytes()));

        WomanArticleDraftEntity saved = draftRepository.save(art);
        return entityToDto.convert(saved);
    }

    @Override
    public PageableDto<ArticleDraftDto> getPage(int page, int size) {
        return null;
    }
}
