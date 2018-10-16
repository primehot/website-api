package web.api.domain.arcticle.woman;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 12.10.2018
 */
@Getter
public enum WomanTopic {
    SEX(1, "Секс"),
    HOLIDAY(2, "Отдых");

    private final Integer id;
    private final String name;

    WomanTopic(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static WomanTopic getById(Integer id) {
        Optional<WomanTopic> o = Arrays.stream(WomanTopic.values()).filter(t -> t.id.equals(id)).findAny();
        return o.orElse(null);
    }

    public static WomanTopic getByName(String name) {
        Optional<WomanTopic> o = Arrays.stream(WomanTopic.values()).filter(t -> t.name.equals(name)).findAny();
        return o.orElse(null);
    }
}

