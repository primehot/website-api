package web.api.dto.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import web.api.dto.AbstractDto;

import java.util.Collection;

/**
 * Created by oleht on 14.10.2018
 */
@Getter
@Setter
@AllArgsConstructor
public class PageableDto <T extends AbstractDto> {
    private Collection<T> items;
    int totalPages;
    long totalElements;
}
