package web.api.domain.arcticle;

import lombok.Getter;
import web.api.dto.unit.HashTagDto;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
public enum HashTag {
    MURDER(1, "#убийство"),
    RELIGY(2, "#религия"),
    INSTAGRAM(3, "#истаграм");

    private final Integer id;
    private final String name;

    HashTag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HashTag getById(Integer id) {
        Optional<HashTag> o = Arrays.stream(HashTag.values()).filter(t -> t.id.equals(id)).findAny();
        return o.orElse(null);
    }

    public static HashTagDto buildById(Integer id) {
        Optional<HashTag> o = Arrays.stream(HashTag.values()).filter(t -> t.id.equals(id)).findAny();
        return o.map(hashTag -> new HashTagDto(hashTag.getId(), hashTag.getName())).orElse(null);
    }
}
