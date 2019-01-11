package web.api.application.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.application.dto.AbstractDto;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
@Setter
public class HashTagDto extends AbstractDto<Integer> {
    private String name;

    public HashTagDto(Integer id, String name) {
        this.setId(id);
        this.name = name;
    }
}
