package web.api.domain.arcticle;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * Created by oleht on 14.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public class AbstractArticleEntity <T extends Number> extends AbstractEntity<T> {

    private String hotContent;
    @Lob
    private String content;
    @Lob
    private Byte[] image;
    private Boolean main = false;

}
