package web.api.application.dto.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.api.application.dto.AbstractDto;

/**
 * Created by oleht on 12.10.2018
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DreamBookDto extends AbstractDto<Long> {
    private String title;
    private String content;
    private String author;
}
