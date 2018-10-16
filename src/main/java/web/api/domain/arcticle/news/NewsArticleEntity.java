package web.api.domain.arcticle.news;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;
import web.api.domain.arcticle.AbstractArticleEntity;

import javax.persistence.Entity;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
public class NewsArticleEntity extends AbstractArticleEntity<Long> {

    private String title;

    private Integer newsTopic;

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic.getId();
    }
}
