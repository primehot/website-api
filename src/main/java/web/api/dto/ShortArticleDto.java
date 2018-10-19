package web.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
public class ShortArticleDto <T extends Number> extends AbstractDto<T> {
    private String shortContent;

    public ShortArticleDto(T id, String shortContent) {
        this.shortContent = shortContent;
        this.setId(id);
    }
}
