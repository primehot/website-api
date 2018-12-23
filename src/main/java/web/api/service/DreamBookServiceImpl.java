package web.api.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.api.converter.dream.DreamBookEntityToDto;
import web.api.converter.dream.DreamBookEntityToShortDto;
import web.api.domain.dream_book.DreamBookEntity;
import web.api.domain.dream_book.DreamBookRankedProjection;
import web.api.domain.dream_book.DreamBookTitlesProjection;
import web.api.dto.component.DreamBookNavigationBarDto;
import web.api.dto.component.DreamTitlePageDto;
import web.api.dto.unit.DreamBookDto;
import web.api.dto.unit.ShortDreamBookDto;
import web.api.repository.DreamBookRepository;
import web.api.util.ShortArticleUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static web.api.util.FTSUtil.or;

/**
 * Created by oleht on 20.11.2018
 */
@Service
public class DreamBookServiceImpl implements DreamBookService {

    private final Sort byDateAndTimes = Sort.by(Sort.Order.asc("creationDate"), Sort.Order.desc("timesVisited"));
    private final Sort byTimes = Sort.by(Sort.Direction.DESC, "timesVisited");

    private DreamBookRepository dreamBookRepository;
    private DreamBookEntityToShortDto toShortDto;
    private DreamBookEntityToDto toDto;

    public DreamBookServiceImpl(DreamBookRepository dreamBookRepository, DreamBookEntityToShortDto toShortDto, DreamBookEntityToDto toDto) {
        this.dreamBookRepository = dreamBookRepository;
        this.toShortDto = toShortDto;
        this.toDto = toDto;
    }

    @Override
    public DreamBookNavigationBarDto getNavigationBarData() {
        List<DreamBookTitlesProjection> mainTitles = dreamBookRepository.findMainTitles(PageRequest.of(0, 9)).getContent();
        Collection<ShortDreamBookDto> topVisited = dreamBookRepository.findAll(PageRequest.of(0, 9, byTimes)).getContent()
                .stream().map(e -> {
                    ShortDreamBookDto d = toShortDto.convert(e);
                    d.setData(ShortArticleUtil.cutShortContent(e.getContent()));
                    return d;
                }).collect(Collectors.toList());

        DreamBookNavigationBarDto dto = new DreamBookNavigationBarDto();
        dto.setMainTitles(mainTitles.stream().map(DreamBookTitlesProjection::getTitle).collect(Collectors.toList()));
        dto.setSeeAlso(topVisited);

        return dto;
    }

    @Override
    public DreamTitlePageDto getDataByTitle(String title) {
        Collection<DreamBookEntity> list = dreamBookRepository.findAllByTitle(title, byDateAndTimes);

        DreamTitlePageDto dto = new DreamTitlePageDto();
        dto.setDreamBooks(list.stream().map(toDto::convert).collect(Collectors.toList()));
        dto.setTitle(title);

        return dto;
    }

    @Override
    public List<DreamBookDto> getDreamBooksByPhrase(String phrase) {
        List<DreamBookRankedProjection> result = dreamBookRepository.getByPhrase(or(phrase), PageRequest.of(0, 30)).getContent();
        return result.stream().map(e -> new DreamBookDto(e.getTitle(), e.getContent(), e.getAuthor())).collect(Collectors.toList());
    }
}
