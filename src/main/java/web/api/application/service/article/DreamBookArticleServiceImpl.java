package web.api.application.service.article;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.converter.article.dream.DreamBookArticleEntityToDto;
import web.api.application.converter.article.dream.DreamBookArticleViewEntityToShortDto;
import web.api.application.domain.ArticleCategory;
import web.api.application.domain.projection.IdProjection;
import web.api.application.domain.entity.arcticle.dream.DreamBookArticleEntity;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ShortArticleDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article.DreamBookArticleRepository;
import web.api.application.repository.article.DreamBookArticleViewRepository;
import web.api.application.util.ArticleUtil;
import web.api.application.util.HashTagUtil;
import web.api.application.util.ImageUtil;

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

    @Autowired
    private ArticleUtil articleUtil;

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
        Optional<ImageProjection> item = Optional.empty();
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
        List<ArticleDto> top10 = dreamBookArticleRepository.findAll(PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent()
                .stream().map(toDto::convert).collect(Collectors.toList());
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        List<ArticleDto> top10 = dreamBookArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent()
                .stream().map(toDto::convert).collect(Collectors.toList());
        return getAdditional(top10);
    }

    private AdditionalArticlesDto getAdditional(List<ArticleDto> top10) {
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setRecommended(top10.subList(0, recommendedSize).stream().map(e -> articleUtil.buildShortArticle(e, ArticleCategory.DREAMBOOK)).collect(Collectors.toList()));

        return dto;
    }
}
