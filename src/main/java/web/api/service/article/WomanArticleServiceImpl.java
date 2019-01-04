package web.api.service.article;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.woman.WomanArticleEntityToDto;
import web.api.domain.arcticle.ArticleCategory;
import web.api.domain.arcticle.ImageProjection;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.domain.arcticle.woman.WomanTopic;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.ArticleNavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.TopicDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.dto.unit.article.ShortArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.WomanArticleRepository;
import web.api.util.HashTagUtil;
import web.api.util.ImageUtil;
import web.api.util.ArticleUtil;

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
    private WomanArticleRepository repository;

    public WomanArticleServiceImpl(WomanArticleEntityToDto toDto, WomanArticleRepository repository) {
        this.toDto = toDto;
        this.repository = repository;
    }

    @Override
    public byte[] getMainImage(Long articleId) {
        Optional<ImageProjection> item = repository.findArticleImageById(articleId);
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + articleId);
    }

    @Override
    public ArticleDto getById(Long id) {
        Optional<WomanArticleEntity> item = repository.findById(id);
        if (item.isPresent()) {
            return toDto.convert(item.get());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + id);
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getPage(int page, int size) {
        Page<WomanArticleEntity> result = repository.findAll(PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getTopicPage(int topicId, int page, int size) {
        Page<WomanArticleEntity> result = repository.findAllByWomanTopic(topicId, PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public ArticleNavigationBarDto getNavigationBarData() {
        List<TopicDto> topics = Arrays.stream(WomanTopic.values()).map(TopicDto::of).collect(Collectors.toList());
        List<ArticleDto> top10 = repository.findAll(PageRequest.of(1, navigationSize, byDateAndTimes))
                .stream().map(e -> toDto.convert(e)).collect(Collectors.toList());

        List<ArticleDto> articles = top10.subList(0, 2);
        articles.forEach(e -> e.setContent(ArticleUtil.cutArticleContent(e.getContent())));
        List<ShortArticleDto> shortArticles = top10.subList(2, 10)
                .stream().map(ArticleUtil::buildShortArticle).collect(Collectors.toList());

        ArticleNavigationBarDto<ArticleDto, ShortArticleDto> dto = new ArticleNavigationBarDto<>();
        dto.setTopics(topics);
        dto.setArticles(articles);
        dto.setSeeAlso(shortArticles.subList(0, 4));
        dto.setMostCommented(shortArticles.subList(4, 8));

        return dto;
    }

    @Override
    public PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size) {
        Page<WomanArticleEntity> result = repository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalArticlesDto getAdditionalArticles() {
        List<WomanArticleEntity> top10 = repository.findAll(PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTopic(int topicId) {
        List<WomanArticleEntity> top10 = repository.findAllByWomanTopic(topicId, PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        List<WomanArticleEntity> top10 = repository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    private AdditionalArticlesDto getAdditional(List<WomanArticleEntity> top10) {
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setNewest(top10.subList(0, newestSize).stream().map(e -> ArticleUtil.buildNewest(e, ArticleCategory.WOMEN)).collect(Collectors.toList()));
        dto.setRecommended(top10.subList(newestSize, newestSize + recommendedSize).stream().map(e -> ArticleUtil.buildShortArticle(e, ArticleCategory.WOMEN)).collect(Collectors.toList()));

        return dto;
    }
}
