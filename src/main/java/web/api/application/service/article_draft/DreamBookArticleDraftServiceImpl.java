package web.api.application.service.article_draft;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.converter.article_draft.dream.DreamBookArticleDraftDtoToEntity;
import web.api.application.converter.article_draft.dream.DreamBookArticleDraftEntityToDto;
import web.api.application.domain.entity.article_draft.dream.DreamBookArticleDraftEntity;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article_draft.DreamBookArticleDraftRepository;
import web.api.application.util.ImageUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by oleht on 12.01.2019
 */
@Service
public class DreamBookArticleDraftServiceImpl implements DreamBookArticleDraftService {
    private DreamBookArticleDraftRepository draftRepository;
    private DreamBookArticleDraftDtoToEntity dtoToEntity;
    private DreamBookArticleDraftEntityToDto entityToDto;

    public DreamBookArticleDraftServiceImpl(DreamBookArticleDraftRepository draftRepository, DreamBookArticleDraftDtoToEntity dtoToEntity, DreamBookArticleDraftEntityToDto entityToDto) {
        this.draftRepository = draftRepository;
        this.dtoToEntity = dtoToEntity;
        this.entityToDto = entityToDto;
    }

    @Override
    public byte[] getMainImage(Long articleId) {
        Optional<ImageProjection> item = draftRepository.findArticleImageById(articleId);
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found DreamBookArticle with id: " + articleId);
    }

    @Override
    public ArticleDraftDto getById(Long id) {
        DreamBookArticleDraftEntity article = draftRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found DreamBookArticle with id: " + id));

        return entityToDto.convert(article);
    }

    @Override
    public ArticleDraftDto save(ArticleDraftDto article, MultipartFile mainImage) throws IOException {
        DreamBookArticleDraftEntity art = dtoToEntity.convert(article);
        art.setMainImage(ImageUtil.convertBytes(mainImage.getBytes()));
        DreamBookArticleDraftEntity saved = draftRepository.save(art);

        return entityToDto.convert(saved);
    }

    @Override
    public PageableDto<ArticleDraftDto> getPage(int page, int size) {
        return null;
    }
}
