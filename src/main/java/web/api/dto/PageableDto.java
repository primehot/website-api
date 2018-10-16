package web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by oleht on 14.10.2018
 */
@Getter
@Setter
@AllArgsConstructor
public class PageableDto<T extends AbstractDto> {
    private Collection<T> items;
    int totalPages;
    long totalElements;
}
