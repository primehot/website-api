package web.api.application.domain.entity.article_draft.news;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.article_draft.AbstractArticleDraftEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "news_article_draft")
public class NewsArticleDraftEntity extends AbstractArticleDraftEntity<Long> {

    private Integer newsTopic;

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic.getId();
    }
}
