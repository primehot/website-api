package web.api.application.domain.entity.dream_book;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.ImageEntity;
import web.api.application.domain.entity.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Getter
@Setter
@Table(name = "dream_book")
public class DreamBookEntity extends AbstractEntity<Long> {

    private String title;
    private String content;

    private Long timesVisited = 0L;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dream_book_images",
            joinColumns = @JoinColumn(name = "dream_book_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<ImageEntity> images;
}
