package web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import web.api.converter.dream.DreamBookEntityToDto;
import web.api.converter.dream.DreamBookEntityToShortDto;
import web.api.domain.dream_book.DreamBookEntity;
import web.api.domain.dream_book.DreamBookEntityRankedEntity;
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

    private DreamBookRepository dreamBookRepository;
    private DreamBookEntityToShortDto toShortDto;
    private DreamBookEntityToDto toDto;
    private ObjectMapper mapper;

    public DreamBookServiceImpl(DreamBookRepository dreamBookRepository, DreamBookEntityToShortDto toShortDto, DreamBookEntityToDto toDto, ObjectMapper mapper) {
        this.dreamBookRepository = dreamBookRepository;
        this.toShortDto = toShortDto;
        this.toDto = toDto;
        this.mapper = mapper;
    }

    @Override
    public DreamBookNavigationBarDto getNavigationBarData() {
        List<Object[]> mainTitles = dreamBookRepository.findMainTitles(PageRequest.of(0, 9)).getContent();
        Collection<ShortDreamBookDto> topVisited = dreamBookRepository.findTopVisited(PageRequest.of(0, 9)).getContent()
                .stream().map(e -> {
                    ShortDreamBookDto d = toShortDto.convert(e);
                    d.setData(ShortArticleUtil.cutShortContent(e.getContent()));
                    return d;
                }).collect(Collectors.toList());

        DreamBookNavigationBarDto dto = new DreamBookNavigationBarDto();
        dto.setMainTitles(mainTitles.stream().map(objects -> objects[0].toString()).collect(Collectors.toList()));
        dto.setSeeAlso(topVisited);

        return dto;
    }

    @Override
    public DreamTitlePageDto getDataByTitle(String title) {
        Collection<DreamBookEntity> list = dreamBookRepository.findAllByTitleOrderByCreationDateAscTimesVisitedAsc(title);

        DreamTitlePageDto dto = new DreamTitlePageDto();
        dto.setDreamBooks(list.stream().map(toDto::convert).collect(Collectors.toList()));
        dto.setTitle(title);

        return dto;
    }

    @Override
    public List<DreamBookDto> getDreamBooksByPhrase(String phrase) {
        List<DreamBookEntityRankedEntity> result = dreamBookRepository.getByPhrase(or(phrase));
        return result.stream().map(e -> new DreamBookDto(e.getTitle(), e.getContent(), e.getAuthor())).collect(Collectors.toList());
    }
}
