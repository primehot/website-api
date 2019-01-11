package web.api.application.dto.component;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.unit.DreamBookDto;

import java.util.List;

/**
 * Created by oleht on 28.11.2018
 */
@Getter
@Setter
public class DreamTitlePageDto {
    private String title;
    private List<DreamBookDto> dreamBooks;
}
