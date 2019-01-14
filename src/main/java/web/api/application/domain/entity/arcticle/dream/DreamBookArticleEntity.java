package web.api.application.domain.entity.arcticle.dream;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "dream_book_article")
public class DreamBookArticleEntity extends AbstractArticleEntity<Long> {

    private String title;

}
