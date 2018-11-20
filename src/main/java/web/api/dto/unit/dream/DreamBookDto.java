package web.api.dto.unit.dream;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
public class DreamBookDto extends AbstractDto<Long> {
    private String title;
    private String content;
    private String author;
}
