package web.api.domain.dream_book;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Getter
@Setter
public class DreamBookEntity extends AbstractEntity<Long> {

    private String title;
    @Lob
    private String content;
    private String author;

}
