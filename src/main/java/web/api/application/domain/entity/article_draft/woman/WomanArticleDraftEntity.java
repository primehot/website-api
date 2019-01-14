package web.api.application.domain.entity.article_draft.woman;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.WomanTopic;
import web.api.application.domain.entity.article_draft.AbstractArticleDraftEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "woman_article_draft")
public class WomanArticleDraftEntity extends AbstractArticleDraftEntity<Long> {

    private Integer womanTopic;

    public void setWomanTopic(WomanTopic womanTopic) {
        this.womanTopic = womanTopic.getId();
    }
}
