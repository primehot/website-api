package web.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.news.NewsArticleEntityToDto;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.dto.PageableDto;
import web.api.dto.news.NewsArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.NewsArticleRepository;
import web.api.util.ImageUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private NewsArticleEntityToDto newsArticleEntityToDto;
    private NewsArticleRepository newsArticleRepository;

    public NewsArticleServiceImpl(NewsArticleEntityToDto newsArticleEntityToDto, NewsArticleRepository newsArticleRepository) {
        this.newsArticleEntityToDto = newsArticleEntityToDto;
        this.newsArticleRepository = newsArticleRepository;
    }

    @Override
    public byte[] getArticleImage(Long articleId) {
        Optional<NewsArticleEntity> item = newsArticleRepository.findById(articleId);
        if(item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found NewsArticle with id: " + articleId);
    }

    @Override
    public NewsArticleDto getById(Long id) {
        Optional<NewsArticleEntity> item = newsArticleRepository.findById(id);
        if(item.isPresent()) {
            return newsArticleEntityToDto.convert(item.get());
        }
        throw new NotFoundException("Not found NewsArticle with id: " + id);
    }

    @Override
    public NewsArticleDto getMain() {
        Optional<NewsArticleEntity> item = newsArticleRepository.findFirstByOrderByCreationDateAsc();
        if(item.isPresent()) {
            return newsArticleEntityToDto.convert(item.get());
        }
        throw new NotFoundException("Not found main NewsArticle");
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<NewsArticleDto> getPage(int page, int size) {
        Page<NewsArticleEntity> result = newsArticleRepository.findAll(PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> newsArticleEntityToDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<NewsArticleDto> getTopicPage(int topicId, int page, int size) {
        Page<NewsArticleEntity> result = newsArticleRepository.findAllByNewsTopic(topicId, PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> newsArticleEntityToDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public Collection<NewsArticleDto> getRecommended() {
        return null;
    }

}
