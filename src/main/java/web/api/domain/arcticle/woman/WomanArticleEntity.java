package web.api.domain.arcticle.woman;

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
public class WomanArticleEntity extends AbstractArticleEntity<Long> {

    private String title;
    private Integer womanTopic;

    public void setNewsTopic(WomanTopic womanTopic) {
        this.womanTopic = womanTopic.getId();
    }
}
