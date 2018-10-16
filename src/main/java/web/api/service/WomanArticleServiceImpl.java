package web.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.api.converter.woman.WomanArticleEntityToDto;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.woman.WomanArticleEntity;
import web.api.dto.PageableDto;
import web.api.dto.news.NewsArticleDto;
import web.api.dto.woman.WomanArticleDto;
import web.api.exception.NotFoundException;
import web.api.repository.WomanArticleRepository;
import web.api.util.ImageUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by oleht on 14.10.2018
 */
@Service
public class WomanArticleServiceImpl implements WomanArticleService {

    private WomanArticleEntityToDto womanArticleEntityToDto;
    private WomanArticleRepository womanArticleRepository;

    public WomanArticleServiceImpl(WomanArticleEntityToDto womanArticleEntityToDto, WomanArticleRepository womanArticleRepository) {
        this.womanArticleEntityToDto = womanArticleEntityToDto;
        this.womanArticleRepository = womanArticleRepository;
    }

    @Override
    public byte[] getArticleImage(Long articleId) {
        Optional<WomanArticleEntity> item = womanArticleRepository.findById(articleId);
        if(item.isPresent()) {
            return ImageUtil.convertBytes(item.get().getImage());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + articleId);
    }

    @Override
    public WomanArticleDto getById(Long id) {
        Optional<WomanArticleEntity> item = womanArticleRepository.findById(id);
        if(item.isPresent()) {
            return womanArticleEntityToDto.convert(item.get());
        }
        throw new NotFoundException("Not found WomanArticle with id: " + id);
    }

    @Override
    public WomanArticleDto getMain() {
        Optional<WomanArticleEntity> item = womanArticleRepository.findFirstByOrderByCreationDateAsc();
        if(item.isPresent()) {
            return womanArticleEntityToDto.convert(item.get());
        }
        throw new NotFoundException("Not found main WomanArticle");
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<WomanArticleDto> getPage(int page, int size) {
        Page<WomanArticleEntity> result = womanArticleRepository.findAll(PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> womanArticleEntityToDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PageableDto<WomanArticleDto> getTopicPage(int topicId, int page, int size) {
        Page<WomanArticleEntity> result = womanArticleRepository.findAllByWomanTopic(topicId, PageRequest.of(page, size));

        return new PageableDto<>(result.getContent().stream().map(e -> womanArticleEntityToDto.convert(e)).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Override
    public Collection<WomanArticleDto> getRecommended() {
        return null;
    }

}
