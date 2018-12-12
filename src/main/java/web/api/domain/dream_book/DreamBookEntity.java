package web.api.domain.dream_book;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Getter
@Setter
@Table(name = "dream_book")
public class DreamBookEntity extends AbstractEntity<Long> {

    private String title;
    private String content;
    private String author;

}
