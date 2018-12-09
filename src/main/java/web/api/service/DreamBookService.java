package web.api.service;

import web.api.dto.component.DreamBookNavigationBarDto;
import web.api.dto.component.DreamTitlePageDto;
import web.api.dto.unit.DreamBookDto;

import java.util.List;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookService {

    DreamBookNavigationBarDto getNavigationBarData();

    DreamTitlePageDto getDataByTitle(String title);

    List<DreamBookDto> getDreamBooksByPhrase(String phrase);

}
