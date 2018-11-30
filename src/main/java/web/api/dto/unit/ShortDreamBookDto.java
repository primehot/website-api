package web.api.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
public class ShortDreamBookDto extends AbstractDto<Long> {
    private String data;
}
