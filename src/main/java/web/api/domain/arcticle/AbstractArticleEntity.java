package web.api.domain.arcticle;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by oleht on 14.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public class AbstractArticleEntity<T extends Number> extends AbstractEntity<T> {

    private String hotContent;
    @Lob
    private String content;
    @Lob
    private Byte[] image;
    private Boolean main = false;
    private Long timesVisited = 0L;

    @ElementCollection(targetClass = Integer.class)
    private Collection<Integer> hashTags = new ArrayList<>();

    public void addHashTag(HashTag hashTag) {
        this.hashTags.add(hashTag.getId());
    }

}
