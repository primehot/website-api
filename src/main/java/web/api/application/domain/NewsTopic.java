package web.api.application.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 12.10.2018
 */
@Getter
public enum NewsTopic {
    POLITICS(1, "Политика"),
    TECHNOLOGY(2, "Технология");

    private final Integer id;
    private final String name;

    NewsTopic(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static NewsTopic getById(Integer id) {
        Optional<NewsTopic> o = Arrays.stream(NewsTopic.values()).filter(t -> t.id.equals(id)).findAny();
        return o.orElse(null);
    }

    public static NewsTopic getByName(String name) {
        Optional<NewsTopic> o = Arrays.stream(NewsTopic.values()).filter(t -> t.name.equals(name)).findAny();
        return o.orElse(null);
    }
}
