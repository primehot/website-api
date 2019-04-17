package web.api.application.domain.entity.arcticle.news;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.ImageEntity;
import web.api.application.domain.NewsTopic;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "news_article")
public class NewsArticleEntity extends AbstractArticleEntity<Long> {

    private Integer newsTopic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_article_images",
            joinColumns = @JoinColumn(name = "news_article_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<ImageEntity> images;

    public void setNewsTopic(NewsTopic newsTopic) {
        this.newsTopic = newsTopic.getId();
    }
}
