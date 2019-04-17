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
import web.api.application.converter.article.news.NewsArticleEntityToDto;
import web.api.application.converter.article.news.NewsArticleEntityViewToShortDto;
import web.api.application.domain.ArticleCategory;
import web.api.application.domain.projection.IdProjection;
import web.api.application.domain.entity.arcticle.news.NewsArticleEntity;
import web.api.application.domain.NewsTopic;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.component.ArticleNavigationBarDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.TopicDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ShortArticleDto;
import web.api.application.exception.NotFoundException;
import web.api.application.repository.article.NewsArticleRepository;
import web.api.application.repository.article.NewsArticleViewRepository;
import web.api.application.util.ArticleUtil;
import web.api.application.util.HashTagUtil;
import web.api.application.util.ImageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Service
@Log
public class NewsArticleServiceImpl implements NewsArticleService {

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

    private NewsArticleEntityToDto toDto;
    private NewsArticleEntityViewToShortDto toShortDto;
    private NewsArticleRepository newsArticleRepository;
    private NewsArticleViewRepository newsArticleViewRepository;

    public NewsArticleServiceImpl(NewsArticleEntityToDto toDto, NewsArticleEntityViewToShortDto toShortDto, NewsArticleRepository newsArticleRepository, NewsArticleViewRepository newsArticleViewRepository) {
        this.toDto = toDto;
        this.toShortDto = toShortDto;
        this.newsArticleRepository = newsArticleRepository;
        this.newsArticleViewRepository = newsArticleViewRepository;
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
        NewsArticleEntity article = newsArticleRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found NewsArticle with id: " + id));
        IdProjection viewId = newsArticleViewRepository.getIdByNewsId(id).orElseThrow(() -> new NotFoundException("Not found NewsArticleView with newsId: " + id));

        ArticleDto dto = toDto.convert(article);
        newsArticleViewRepository.findById(viewId.getId() - 1).ifPresent(e -> dto.setPrevious(toShortDto.convert(e)));
        newsArticleViewRepository.findById(viewId.getId() + 1).ifPresent(e -> dto.setNext(toShortDto.convert(e)));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getPage(int page, int size) {
        validatePageRequest(page, size);
        Page<NewsArticleEntity> result = newsArticleRepository.findAll(PageRequest.of(page, size, byDateAndTimes));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<ArticleDto> getTopicPage(int topicId, int page, int size) {
        validatePageRequest(page, size);
        Page<NewsArticleEntity> result = newsArticleRepository.findAllByNewsTopic(topicId, PageRequest.of(page, size, byDateAndTimes));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size) {
        validatePageRequest(page, size);
        Page<NewsArticleEntity> result = newsArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(page, size, byDateAndTimes));

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
    public ArticleNavigationBarDto getNavigationBarData() {
        List<TopicDto> topics = Arrays.stream(NewsTopic.values()).map(TopicDto::of).collect(Collectors.toList());
        List<ArticleDto> top10 = newsArticleRepository.findAll(PageRequest.of(1, navigationSize, byDateAndTimes))
                .stream().map(e -> toDto.convert(e)).collect(Collectors.toList());

        List<ArticleDto> articles = top10.subList(0, 2);
        articles.forEach(e -> e.setContent(articleUtil.cutArticleContent(e.getContent())));
        List<ShortArticleDto> shortArticles = top10.subList(2, 10)
                .stream().map(e -> articleUtil.buildShortArticle(e, ArticleCategory.NEWS)).collect(Collectors.toList());

        ArticleNavigationBarDto<ArticleDto, ShortArticleDto> dto = new ArticleNavigationBarDto<>();
        dto.setTopics(topics);
        dto.setArticles(articles);
        dto.setSeeAlso(shortArticles.subList(0, 4));
        dto.setMostCommented(shortArticles.subList(4, 8));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public AdditionalArticlesDto getAdditionalArticles() {
        List<NewsArticleEntity> top10 = newsArticleRepository.findAll(PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTopic(int topicId) {
        List<NewsArticleEntity> top10 = newsArticleRepository.findAllByNewsTopic(topicId, PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        List<NewsArticleEntity> top10 = newsArticleRepository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(0, recommendedSize + newestSize, byDateAndTimes)).getContent();
        return getAdditional(top10);
    }

    private AdditionalArticlesDto getAdditional(List<NewsArticleEntity> top10) {
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setNewest(top10.subList(0, newestSize).stream().map(e -> ArticleUtil.buildNewest(e, ArticleCategory.NEWS)).collect(Collectors.toList()));
        dto.setRecommended(top10.subList(newestSize, newestSize + recommendedSize).stream().map(e -> articleUtil.buildShortArticle(e, ArticleCategory.NEWS)).collect(Collectors.toList()));

        return dto;
    }
}
