package web.api.application.domain.entity.arcticle.woman;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.ImageEntity;
import web.api.application.domain.WomanTopic;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "woman_article")
public class WomanArticleEntity extends AbstractArticleEntity<Long> {

    private Integer womanTopic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "woman_article_images",
            joinColumns = @JoinColumn(name = "woman_article_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<ImageEntity> images;

    public void setWomanTopic(WomanTopic womanTopic) {
        this.womanTopic = womanTopic.getId();
    }
}
