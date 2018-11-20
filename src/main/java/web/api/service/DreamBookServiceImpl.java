package web.api.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.api.converter.dream.DreamBookEntityToShortDto;
import web.api.dto.component.DreamBookNavigationBarDto;
import web.api.dto.unit.dream.ShortDreamBookDto;
import web.api.repository.DreamBookRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by oleht on 20.11.2018
 */
@Service
public class DreamBookServiceImpl implements DreamBookService {

    private DreamBookRepository dreamBookRepository;
    private DreamBookEntityToShortDto toShortDto;

    public DreamBookServiceImpl(DreamBookRepository dreamBookRepository, DreamBookEntityToShortDto toShortDto) {
        this.dreamBookRepository = dreamBookRepository;
        this.toShortDto = toShortDto;
    }

    @Override
    public DreamBookNavigationBarDto getNavigationBarData() {
        Collection<String> mainTitles = dreamBookRepository.findMainTitles(PageRequest.of(0, 9)).getContent();
        Collection<ShortDreamBookDto> topVisited = dreamBookRepository.findTopVisited(PageRequest.of(0, 10)).getContent()
                .stream().map(toShortDto::convert).collect(Collectors.toList());

        DreamBookNavigationBarDto dto = new DreamBookNavigationBarDto();
        dto.setMainTitles(mainTitles);
        dto.setSeeAlso(topVisited);

        return dto;
    }
}
