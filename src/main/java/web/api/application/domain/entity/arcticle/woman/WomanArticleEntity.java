package web.api.application.domain.entity.arcticle.woman;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.WomanTopic;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "woman_article")
public class WomanArticleEntity extends AbstractArticleEntity<Long> {

    private String title;
    private Integer womanTopic;

    public void setWomanTopic(WomanTopic womanTopic) {
        this.womanTopic = womanTopic.getId();
    }
}
