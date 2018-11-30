package web.api.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.unit.ShortDreamBookDto;

import java.util.Collection;

/**
 * Created by oleht on 20.11.2018
 */
@Getter
@Setter
public class DreamBookNavigationBarDto {
    Collection<String> mainTitles;
    Collection<ShortDreamBookDto> seeAlso;
}
