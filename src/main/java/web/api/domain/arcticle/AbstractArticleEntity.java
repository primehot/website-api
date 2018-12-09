package web.api.domain.arcticle;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.AbstractEntity;

import javax.persistence.MappedSuperclass;
import java.util.Arrays;
import java.util.stream.Collectors;

import static web.api.util.HashTagUtil.hashTagSeparator;
import static web.api.util.HashTagUtil.wrapHashTag;

/**
 * Created by oleht on 14.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public class AbstractArticleEntity<T extends Number> extends AbstractEntity<T> {

    private String hotContent;
    private String content;
    private Byte[] image;
    private Boolean main = false;

    private String hashTags = "";

    public void addHashTag(HashTag hashTag) {
        if (Arrays.stream(this.hashTags.split(hashTagSeparator)).noneMatch(wrapHashTag(hashTag.getId())::equals)) {
            this.hashTags += wrapHashTag(hashTag.getId()) + hashTagSeparator;
        }
    }

    public void removeHashTag(HashTag hashTag) {
        this.hashTags = Arrays.stream(this.hashTags.split(hashTagSeparator)).filter(i -> i.equals(wrapHashTag(hashTag.getId()))).collect(Collectors.joining(hashTagSeparator));
    }
}
