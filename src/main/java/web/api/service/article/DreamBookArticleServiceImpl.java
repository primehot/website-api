package web.api.service.article;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.dream.DreamBookArticleEntityToDto;
import web.api.converter.dream.DreamBookArticleViewEntityToShortDto;
import web.api.domain.arcticle.ArticleCategory;
import web.api.domain.arcticle.IdProjection;
import web.api.domain.arcticle.ImageProjection;
import web.api.domain.arcticle.dream.DreamBookArticleEntity;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.dto.unit.article.ShortArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.DreamBookArticleRepository;
import web.api.repository.DreamBookArticleViewRepository;
import web.api.util.ArticleUtil;
import web.api.util.HashTagUtil;
import web.api.util.ImageUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 04.01.2019
 */
@Service
@Log
public class DreamBookArticleServiceImpl implements DreamBookArticleService {
    private final Sort byDateAndTimes = Sort.by(Sort.Order.asc("creationDate"), Sort.Order.desc("timesVisited"));

    @Value("${page.size}")
    private Integer pageSize;
    @Value("${recommended.size}")
    private Integer recommendedSize;
    @Value("${newest.size}")
    private Integer newestSize;
    @Value("${navigation.size}")
    private Integer navigationSize;

    private DreamBookArticleEntityToDto toDto;
    private DreamBookArticleViewEntityToShortDto toShortDto;
    private DreamBookArticleRepository dreamBookArticleRepository;
    private DreamBookArticleViewRepository dreamBookArticleViewRepository;

    public DreamBookArticleServiceImpl(DreamBookArticleEntityToDto toDto, DreamBookArticleViewEntityToShortDto toShortDto, DreamBookArticleRepository dreamBookArticleRepository, DreamBookArticleViewRepository dreamBookArticleViewRepository) {
        this.toDto = toDto;
        this.toShortDto = toShortDto;
        this.dreamBookArticleRepository = dreamBookArticleRepository;
        this.dreamBookArticleViewRepository = dreamBookArticleViewRepository;
    }

    @Override
    public byte[] getMainImage(Long articleId) {
        Optional<ImageProjection> item = dreamBookArticleRepository.findArticleImageById(articleId);
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found NewsArticle with id: " + articleId);
    }

    @Override
    public ArticleDto getById(Long id) {
        DreamBookArticleEntity article = dreamBookArticleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found DreamBookArticle with id: " + id));
        IdProjection viewId = dreamBookArticleViewRepository.getIdByDreamBookId(id).orElseThrow(() -> new NotFoundException("Not found DreamBookArticleView with newsId: " + id));

        ArticleDto dto = toDto.convert(article);
        dreamBookArticleViewRepository.findById(viewId.getId() - 1).ifPresent(e -> dto.setPrevious(toShortDto.convert(e)));
        dreamBookArticleViewRepository.findById(viewId.getId() + 1).ifPresent(e -> dto.setNext(toShortDto.convert(e)));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getPage(int page, int size) {
        validatePageRequest(page, size);
        Page<DreamBookArticleEntity> result = dreamBookArticleRepository.findAll(PageRequest.of(page, size, byDateAndTimes));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size) {
        validatePageRequest(page, size);
        Page<DreamBookArticleEntity> result = dreamBookArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(page, size, byDateAndTimes));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    private void validatePageRequest(int page, int size) {
        if (size != pageSize) {
            log.severe("The requested page size is" + size + ", when standard is " + pageSize);
        }
        if (page == 0) {
            log.severe("Illegal page request. The 0 page was requested");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalArticlesDto getAdditionalArticles() {
        List<DreamBookArticleEntity> top10 = dreamBookArticleRepository.findAll(PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        List<DreamBookArticleEntity> top10 = dreamBookArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    private AdditionalArticlesDto getAdditional(List<DreamBookArticleEntity> top10) {
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setRecommended(top10.subList(0, recommendedSize).stream().map(e -> ArticleUtil.buildShortArticle(e, ArticleCategory.DREAMBOOK)).collect(Collectors.toList()));

        return dto;
    }
}