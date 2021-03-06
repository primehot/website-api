package web.api.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import web.api.application.domain.projection.ArticleRankedProjection;
import web.api.application.dto.component.AdditionalArticlesDto;
import web.api.application.dto.unit.PageableDto;
import web.api.application.dto.unit.article.AbstractArticleCategoryDto;
import web.api.application.dto.unit.article.ArticleCategoryDto;
import web.api.application.dto.unit.article.ArticleDto;
import web.api.application.dto.unit.article.ShortArticleDto;
import web.api.application.repository.article.NewsArticleRepository;
import web.api.application.repository.article.WomanArticleRepository;
import web.api.application.service.article.NewsArticleService;
import web.api.application.service.article.WomanArticleService;
import web.api.application.util.HashTagUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static web.api.application.util.FTSUtil.or;
import static web.api.application.util.PageUtil.getSizes;

/**
 * Created by oleht on 12.12.2018
 */

@Service
public class CustomServiceImpl implements CustomService {

    @Value("${recommended.size}")
    private Integer recommendedSize;
    @Value("${newest.size}")
    private Integer newestSize;

    private WomanArticleRepository womanArticleRepository;
    private NewsArticleRepository newsArticleRepository;

    private WomanArticleService womanArticleService;
    private NewsArticleService newsArticleService;

    public CustomServiceImpl(WomanArticleRepository womanArticleRepository, NewsArticleRepository newsArticleRepository, WomanArticleService womanArticleService, NewsArticleService newsArticleService) {
        this.womanArticleRepository = womanArticleRepository;
        this.newsArticleRepository = newsArticleRepository;
        this.womanArticleService = womanArticleService;
        this.newsArticleService = newsArticleService;
    }

    @Override
    public PageableDto<ArticleDto> getByPhrase(String phrase, int page, int size) {
        Pair<Integer, Integer> sizes = getSizes(size);
        Page<ArticleRankedProjection> womanArticles = womanArticleRepository.getByPhrase(or(phrase), PageRequest.of(page, sizes.getFirst()));
        Page<ArticleRankedProjection> newsArticles = newsArticleRepository.getByPhrase(or(phrase), PageRequest.of(page, sizes.getFirst()));

        List<ArticleDto> result = getAllItems(womanArticles.getContent(), newsArticles.getContent());

        return new PageableDto<>(result, newsArticles.getTotalPages() + womanArticles.getTotalPages(), newsArticles.getTotalElements() + womanArticles.getTotalElements());
    }

    @Override
    public PageableDto getTagsPage(int id, int page, int size) {
        Pair<Integer, Integer> sizes = getSizes(size);
        PageableDto<ArticleDto> news = newsArticleService.getHashTagPage(id, page, sizes.getFirst());
        PageableDto<ArticleDto> woman = womanArticleService.getHashTagPage(id, page, sizes.getSecond());

        Collection<AbstractArticleCategoryDto> allItems = new ArrayList<>();
        allItems.addAll(news.getItems());
        allItems.addAll(woman.getItems());

        return new PageableDto<>(allItems, news.getTotalPages() + woman.getTotalPages(), news.getTotalElements() + woman.getTotalElements());
    }

    @Override
    public AdditionalArticlesDto getAdditionalData() {
        AdditionalArticlesDto newAdditional = newsArticleService.getAdditionalArticles();
        AdditionalArticlesDto womanAdditional = womanArticleService.getAdditionalArticles();

        return getAdditional(newAdditional, womanAdditional);
    }

    @Override
    public AdditionalArticlesDto getAdditionalArticlesByTag(int hashTagId) {
        AdditionalArticlesDto newAdditional = newsArticleService.getAdditionalArticlesByTag(hashTagId);
        AdditionalArticlesDto womanAdditional = womanArticleService.getAdditionalArticlesByTag(hashTagId);

        return getAdditional(newAdditional, womanAdditional);
    }

    private AdditionalArticlesDto getAdditional(AdditionalArticlesDto newAdditional, AdditionalArticlesDto womanAdditional) {
        Collection<ShortArticleDto> recommended = new ArrayList<>();
        for (int i = 0; i < recommendedSize; i++) {
            if (i % 2 == 0) {
                ShortArticleDto item = (ShortArticleDto) newAdditional.getRecommended().iterator().next();
                recommended.add(item);
            } else {
                ShortArticleDto item = (ShortArticleDto) womanAdditional.getRecommended().iterator().next();
                recommended.add(item);
            }
        }

        Collection<ShortArticleDto> newest = new ArrayList<>();
        for (int i = 0; i < newestSize; i++) {
            if (i % 2 == 0) {
                ShortArticleDto item = (ShortArticleDto) newAdditional.getNewest().iterator().next();
                newest.add(item);
            } else {
                ShortArticleDto item = (ShortArticleDto) womanAdditional.getNewest().iterator().next();
                newest.add(item);
            }
        }

        AdditionalArticlesDto<ShortArticleDto> tagsAdditional = new AdditionalArticlesDto<>();
        tagsAdditional.setRecommended(recommended);
        tagsAdditional.setNewest(newest);

        return tagsAdditional;
    }

    private List<ArticleDto> getAllItems(List<ArticleRankedProjection> womanArticles, List<ArticleRankedProjection> newsArticles) {
        Collection<Pair<ArticleCategoryDto, ArticleRankedProjection>> allItems = new ArrayList<>();
        allItems.addAll(newsArticles.stream().map(e -> Pair.of(ArticleCategoryDto.getNewsCategory(), e)).collect(Collectors.toList()));
        allItems.addAll(womanArticles.stream().map(e -> Pair.of(ArticleCategoryDto.getWomanCategory(), e)).collect(Collectors.toList()));

        return allItems.stream()
                .sorted(Comparator.comparing(o -> o.getSecond().getRank()))
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ArticleDto convert(Pair<ArticleCategoryDto, ArticleRankedProjection> from) {
        ArticleDto dto = new ArticleDto();
        dto.setId(from.getSecond().getId());
        dto.setTitle(from.getSecond().getTitle());
        dto.setTopic(from.getSecond().getTopic());
        dto.setHotContent(from.getSecond().getHotContent());
        dto.setContent(from.getSecond().getContent());
        dto.setArticleCategory(from.getFirst());
        HashTagUtil.getHashTags(from.getSecond().getHashTags()).forEach(dto::addHashTag);

        return dto;
    }

}
