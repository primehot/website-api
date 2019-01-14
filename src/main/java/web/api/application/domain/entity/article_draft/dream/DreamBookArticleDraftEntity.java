package web.api.application.domain.entity.article_draft.dream;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.entity.article_draft.AbstractArticleDraftEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "dream_book_article_draft")
public class DreamBookArticleDraftEntity extends AbstractArticleDraftEntity<Long> {

}
