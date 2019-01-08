package web.api.domain.view;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 08.01.2019
 */
@Entity
@Table(name = "woman_article_view")
public class WomanArticleViewEntity extends AbstractViewEntity<Long> {

    private Long womanId;

    public Long getWomanId() {
        return womanId;
    }

    public void setWomanId(Long womanId) {
        this.womanId = womanId;
    }
}
