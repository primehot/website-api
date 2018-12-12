package web.api.service.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.woman.WomanArticleEntityToDto;
import web.api.domain.arcticle.HashTag;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.domain.arcticle.woman.WomanTopic;
import web.api.dto.component.AdditionalArticlesDto;
import web.api.dto.component.ArticleNavigationBarDto;
import web.api.dto.unit.PageableDto;
import web.api.dto.unit.TopicDto;
import web.api.dto.unit.article.ArticleCategoryDto;
import web.api.dto.unit.article.ArticleDto;
import web.api.dto.unit.article.ShortArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.WomanArticleRepository;
import web.api.util.HashTagUtil;
import web.api.util.ImageUtil;
import web.api.util.ShortArticleUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Service
public class WomanArticleServiceImpl implements WomanArticleService {

    private WomanArticleEntityToDto toDto;
    private WomanArticleRepository repository;

    public WomanArticleServiceImpl(WomanArticleEntityToDto toDto, WomanArticleRepository repository) {
        this.toDto = toDto;
        this.repository = repository;
    }

    @Override
    public byte[] getArticleImage(Long articleId) {
        Optional<WomanArticleEntity> item = repository.findById(articleId);
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
    public ArticleDto getMain() {
        Optional<WomanArticleEntity> item = repository.findFirstByOrderByCreationDateAsc();
        if (item.isPresent()) {
            return toDto.convert(item.get());
        }
        throw new NotFoundException("Not found main WomanArticle");
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
        List<TopicDto> topics = Arrays.stream(WomanTopic.values()).map(e -> new TopicDto(e.getId(), e.toString(), e.getName())).collect(Collectors.toList());
        List<ArticleDto> top10 = repository.findTop10ByOrderByCreationDateAscTimesVisitedAsc()
                .stream().map(e -> toDto.convert(e)).collect(Collectors.toList());

        List<ArticleDto> articles = top10.subList(0, 2);
        articles.forEach(e -> e.setContent(ShortArticleUtil.cutArticleContent(e.getContent())));
        List<ShortArticleDto> shortArticles = top10.subList(2, 10)
                .stream().map(this::buildShortArticle).collect(Collectors.toList());

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
        AdditionalArticlesDto<ShortArticleDto> dto = new AdditionalArticlesDto<>();
        dto.setRecommended(getRecommended());
        dto.setNewest(getNewest());

        return dto;
    }

    @Override
    public PageableDto<ArticleDto> getHashTagPage(int hashTagId, int page, int size) {
        Page<WomanArticleEntity> result = repository.findAllByHashTag(HashTagUtil.wrapHashTag(hashTagId), PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    private ShortArticleDto buildShortArticle(ArticleDto e) {
        return new ShortArticleDto<>(e.getId(), ShortArticleUtil.cutShortContent(e.getContent()), ArticleCategoryDto.getWomanCategory(), e.getHashTags());
    }

    private ShortArticleDto buildShortArticle(WomanArticleEntity e) {
        return new ShortArticleDto<>(e.getId(), ShortArticleUtil.cutShortContent(e.getContent()), ArticleCategoryDto.getWomanCategory(), HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                .collect(Collectors.toList()));
    }

    private Collection<ShortArticleDto> getRecommended() {
        Instant i = Instant.now().minus(recommendedFromDay, ChronoUnit.DAYS);
        Timestamp dateBefore = Timestamp.from(i);

        List<WomanArticleEntity> recommended = repository.getRecommended(dateBefore, PageRequest.of(0, recommendedSize));

        return recommended.stream()
                .map(this::buildShortArticle)
                .collect(Collectors.toList());
    }

    private Collection<ShortArticleDto> getNewest() {
        return repository.findTop4ByOrderByCreationDateAsc().stream()
                .map(e -> new ShortArticleDto<>(e.getId(), e.getHotContent(), ArticleCategoryDto.getWomanCategory(),
                        HashTagUtil.getHashTags(e).stream().map(HashTag::buildById)
                                .collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
