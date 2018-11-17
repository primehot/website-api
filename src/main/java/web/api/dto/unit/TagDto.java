package web.api.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 16.10.2018
 */
@Getter
@Setter
public class TagDto extends AbstractDto<Integer> {
    private String value;
    private String name;

    public TagDto(Integer id, String value, String name) {
        this.setId(id);
        this.value = value;
        this.name = name;
    }
}
