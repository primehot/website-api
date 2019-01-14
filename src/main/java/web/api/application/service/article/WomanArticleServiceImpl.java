package web.api.application.service.article;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.application.domain.ArticleCategory;
import web.api.application.domain.projection.IdProjection;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.domain.entity.arcticle.woman.WomanArticleEntity;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.component.ArticleNavigationBarDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article.WomanArticleRepository;
import web.api.application.repository.article.WomanArticleViewRepository;
import web.api.application.util.ImageUtil;
import web.api.application.converter.article.woman.WomanArticleEntityToDto;
import web.api.application.converter.article.woman.WomanArticleViewEntityToShortDto;
import web.api.application.domain.WomanTopic;
import web.api.application.dto.unit.article.ShortArticleDto;
import web.api.application.util.ArticleUtil;
import web.api.application.util.HashTagUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Service
public class WomanArticleServiceImpl implements WomanArticleService {

    private final Sort byDateAndTimes = Sort.by(Sort.Order.asc("creationDate"), Sort.Order.desc("timesVisited"));

    @Value("${page.size}")
    private Integer pageSize;
    @Value("${recommended.size}")
    private Integer recommendedSize;
    @Value("${newest.size}")
    private Integer newestSize;
    @Value("${navigation.size}")
    private Integer navigationSize;

    private WomanArticleEntityToDto toDto;
    private WomanArticleViewEntityToShortDto toShortDto;
    private WomanArticleRepository womanArticleRepository;
    private WomanArticleViewRepository womanArticleViewRepository;

    public WomanArticleServiceImpl(WomanArticleEntityToDto toDto, WomanArticleViewEntityToShortDto toShortDto, WomanArticleRepository womanArticleRepository, WomanArticleViewRepository womanArticleViewRepository) {
        this.toDto = toDto;
        this.toShortDto = toShortDto;
        this.womanArticleRepository = womanArticleRepository;
        this.womanArticleViewRepository = womanArticleViewRepository;
    }

    @Override
    public byte[] getMainImage(Long articleId) {
        Optional<ImageProjection> item = womanArticleRepository.findArticleImageById(articleId);
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + articleId);
    }

    @Override
    public ArticleDto getById(Long id) {
        WomanArticleEntity article = womanArticleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found WomanArticle with id: " + id));
        IdProjection viewId = womanArticleViewRepository.getIdByWomanId(id).orElseThrow(() -> new NotFoundException("Not found WomanArticleView with newsId: " + id));

        ArticleDto dto = toDto.convert(article);
        womanArticleViewRepository.findById(viewId.getId() - 1).ifPresent(e -> dto.setPrevious(toShortDto.convert(e)));
        womanArticleViewRepository.findById(viewId.getId() + 1).ifPresent(e -> dto.setNext(toShortDto.convert(e)));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getPage(int page, int size) {
        Page<WomanArticleEntity> result = womanArticleRepository.findAll(PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getTopicPage(int topicId, int page, int size) {
        Page<WomanArticleEntity> result = womanArticleRepository.findAllByWomanTopic(topicId, PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public ArticleNavigationBarDto getNavigationBarData() {
        List<TopicDto> topics = Arrays.stream(WomanTopic.values()).map(TopicDto::of).collect(Collectors.toList());
        List<ArticleDto> top10 = womanArticleRepository.findAll(PageRequest.of(1, navigationSize, byDateAndTimes))
                .stream().map(e -> toDto.convert(e)).collect(Collectors.toList());

        List<ArticleDto> articles = top10.subList(0, 2);
        articles.forEach(e -> e.setContent(ArticleUtil.cutArticleContent(e.getContent())));
        List<ShortArticleDto> shortArticles = top10.subList(2, 10)
                .stream().map(e -> ArticleUtil.buildShortArticle(e, ArticleCategory.WOMEN)).collect(Collectors.toList());

        ArticleNavigationBarDto<ArticleDto, ShortArticleDto> dto = new ArticleNavigationBarDto<>();
        dto.setTopics(topics);
        dto.setArticles(articles);
        dto.setSeeAlso(shortArticles.subList(0, 4));
        dto.setMostCommented(shortArticles.subList(4, 8));

        return dto;
    }

    @Override
    public PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size) {
        Page<WomanArticleEntity> result = womanArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalArticlesDto getAdditionalArticles() {
        List<WomanArticleEntity> top10 = womanArticleRepository.findAll(PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTopic(int topicId) {
        List<WomanArticleEntity> top10 = womanArticleRepository.findAllByWomanTopic(topicId, PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        List<WomanArticleEntity> top10 = womanArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    private AdditionalArticlesDto getAdditional(List<WomanArticleEntity> top10) {
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setNewest(top10.subList(0, newestSize).stream().map(e -> ArticleUtil.buildNewest(e, ArticleCategory.WOMEN)).collect(Collectors.toList()));
        dto.setRecommended(top10.subList(newestSize, newestSize + recommendedSize).stream().map(e -> ArticleUtil.buildShortArticle(e, ArticleCategory.WOMEN)).collect(Collectors.toList()));

        return dto;
    }
}
