package web.api.application.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 12.10.2018
 */
@Getter
@Setter
public abstract class AbstractDto<T extends Number> {
    private T id;
}
