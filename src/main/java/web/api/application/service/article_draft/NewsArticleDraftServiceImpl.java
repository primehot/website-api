package web.api.application.service.article_draft;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.api.application.converter.article.news.NewsArticleEntityToDto;
import web.api.application.converter.article_draft.news.NewsArticleDraftDtoToEntity;
import web.api.application.converter.article_draft.news.NewsArticleDraftEntityToDto;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.entity.article_draft.news.NewsArticleDraftEntity;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article_draft.ArticleDraftDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article_draft.NewsArticleDraftRepository;
import web.api.application.util.ImageUtil;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by oleht on 12.01.2019
 */
@Service
public class NewsArticleDraftServiceImpl implements NewsArticleDraftService {

    private NewsArticleDraftRepository draftRepository;
    private NewsArticleDraftDtoToEntity dtoToEntity;
    private NewsArticleDraftEntityToDto entityToDto;

    public NewsArticleDraftServiceImpl(NewsArticleDraftRepository draftRepository, NewsArticleDraftDtoToEntity dtoToEntity, NewsArticleDraftEntityToDto entityToDto) {
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
        throw new NotFoundException("Not found NewsArticle with id: " + articleId);
    }

    @Override
    public ArticleDraftDto getById(Long id) {
        NewsArticleDraftEntity article = draftRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found NewsArticle with id: " + id));

        return entityToDto.convert(article);
    }

    @Override
    public ArticleDraftDto save(ArticleDraftDto article, MultipartFile mainImage[]) throws IOException {
        NewsArticleDraftEntity art = dtoToEntity.convert(article);
//        art.setMainImage(ImageUtil.convertBytes(mainImage.getBytes()));

        NewsArticleDraftEntity saved = draftRepository.save(art);
        return entityToDto.convert(saved);
    }

    @Override
    public PageableDto<ArticleDraftDto> getPage(int page, int size) {
        return null;
    }
}
