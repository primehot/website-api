package web.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.news.NewsArticleEntityToDto;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.dto.*;
import web.api.dto.news.NewsArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.NewsArticleRepository;
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
public class NewsArticleServiceImpl implements NewsArticleService {

    private NewsArticleEntityToDto toDto;
    private NewsArticleRepository newsArticleRepository;

    public NewsArticleServiceImpl(NewsArticleEntityToDto newsArticleEntityToDto, NewsArticleRepository newsArticleRepository) {
        this.toDto = newsArticleEntityToDto;
        this.newsArticleRepository = newsArticleRepository;
    }

    @Override
    public byte[] getArticleImage(Long articleId) {
        Optional<NewsArticleEntity> item = newsArticleRepository.findById(articleId);
        if (item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found NewsArticle with id: " + articleId);
    }

    @Override
    public NewsArticleDto getById(Long id) {
        Optional<NewsArticleEntity> item = newsArticleRepository.findById(id);
        if (item.isPresent()) {
            return toDto.convert(item.get());
        }
        throw new NotFoundException("Not found NewsArticle with id: " + id);
    }

    @Override
    public NewsArticleDto getMain() {
        Optional<NewsArticleEntity> item = newsArticleRepository.findFirstByOrderByCreationDateAscTimesVisitedAsc();
        if (item.isPresent()) {
            return toDto.convert(item.get());
        }
        throw new NotFoundException("Not found main NewsArticle");
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<NewsArticleDto> getPage(int page, int size) {
        Page<NewsArticleEntity> result = newsArticleRepository.findAll(PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<NewsArticleDto> getTopicPage(int topicId, int page, int size) {
        Page<NewsArticleEntity> result = newsArticleRepository.findAllByNewsTopic(topicId, PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> toDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public NavigationBarDto getNavigationBarData() {
        List<TopicDto> topics = Arrays.stream(NewsTopic.values()).map(e -> new TopicDto(e.getId(), e.toString(), e.getName())).collect(Collectors.toList());
        List<NewsArticleDto> top10 = newsArticleRepository.findTop10ByOrderByCreationDateAscTimesVisitedAsc()
                .stream().map(e -> toDto.convert(e)).collect(Collectors.toList());

        List<NewsArticleDto> articles = top10.subList(0, 2);
        articles.forEach(e -> e.setContent(ShortArticleUtil.cutArticleContent(e.getContent())));
        List<ShortArticleDto> shortArticles = top10.subList(2, 10)
                .stream().map(e -> new ShortArticleDto(e.getId(), ShortArticleUtil.cutShortContent(e.getContent()))).collect(Collectors.toList());

        NavigationBarDto<NewsArticleDto, ShortArticleDto> navigationBarDto = new NavigationBarDto<>();
        navigationBarDto.setTopics(topics);
        navigationBarDto.setArticles(articles);
        navigationBarDto.setSeeAlso(shortArticles.subList(0, 4));
        navigationBarDto.setMostCommented(shortArticles.subList(4, 8));

        return navigationBarDto;
    }

    @Override
    public Collection<ShortArticleDto> getRecommended() {
        Instant i = Instant.now().minus(recommendedFromDay, ChronoUnit.DAYS);
        Timestamp dateBefore = Timestamp.from(i);

        List<NewsArticleEntity> recommended = newsArticleRepository.getRecommended(dateBefore, PageRequest.of(0, recommendedSize));
        List<ShortArticleDto> result = recommended.stream().map(e -> new ShortArticleDto(e.getId(), ShortArticleUtil.cutShortContent(e.getContent()))).collect(Collectors.toList());
        return result;
    }

    @Override
    public Collection<NewsArticleDto> getLast() {
        return null;
    }

}
