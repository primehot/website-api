package web.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 12.10.2018
 */
@Getter
@Setter
public class AbstractDto<T extends Number> {
    private T id;
}
