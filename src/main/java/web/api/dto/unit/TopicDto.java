package web.api.dto.unit;

import lombok.Getter;
import lombok.Setter;
import web.api.domain.arcticle.news.NewsTopic;
import web.api.domain.arcticle.woman.WomanTopic;
import web.api.dto.AbstractDto;

/**
 * Created by oleht on 16.10.2018
 */
@Getter
@Setter
public class TopicDto extends AbstractDto<Integer> {
    private String value;
    private String name;

    private TopicDto(Integer id, String value, String name) {
        this.setId(id);
        this.value = value;
        this.name = name;
    }

    public static TopicDto of(NewsTopic newsTopic) {
        return new TopicDto(newsTopic.getId(), newsTopic.toString(), newsTopic.getName());
    }

    public static TopicDto of(WomanTopic womanTopic) {
        return new TopicDto(womanTopic.getId(), womanTopic.toString(), womanTopic.getName());
    }

}
