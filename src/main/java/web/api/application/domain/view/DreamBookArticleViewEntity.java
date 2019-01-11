package web.api.application.domain.view;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 08.01.2019
 */
@Entity
@Table(name = "dream_book_article_view")
public class DreamBookArticleViewEntity extends AbstractViewEntity<Long> {

    private Long dreamBookId;

    public Long getDreamBookId() {
        return dreamBookId;
    }

    public void setDreamBookId(Long dreamBookId) {
        this.dreamBookId = dreamBookId;
    }
}
