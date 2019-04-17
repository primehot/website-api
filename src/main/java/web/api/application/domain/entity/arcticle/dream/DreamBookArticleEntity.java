package web.api.application.domain.entity.arcticle.dream;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.ImageEntity;
import web.api.application.domain.entity.arcticle.AbstractArticleEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
@Table(name = "dream_book_article")
public class DreamBookArticleEntity extends AbstractArticleEntity<Long> {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dream_book_article_images",
            joinColumns = @JoinColumn(name = "dream_book_article_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<ImageEntity> images;

}
