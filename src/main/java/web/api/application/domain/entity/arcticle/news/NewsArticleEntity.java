package web.api.application.domain.entity.arcticle.news;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "news_article")
public class NewsArticleEntity extends AbstractArticleEntity<Long> {

    private String title;

    private Integer newsTopic;

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic.getId();
    }
}
