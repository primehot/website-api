package web.api.domain.arcticle.dream;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.arcticle.AbstractArticleEntity;

import javax.persistence.Entity;

/**
 * Created by oleht on 12.10.2018
 */
@Entity
@Setter
@Getter
public class DreamBookArticleEntity extends AbstractArticleEntity<Long> {

    private String title;

}
