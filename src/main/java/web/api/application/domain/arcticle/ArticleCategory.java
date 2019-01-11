package web.api.application.domain.arcticle;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 13.11.2018
 */
@Getter
public enum ArticleCategory {
    NEWS(1, "news"),
    WOMEN(2, "women"),
    DREAM(3, "dream"),
    DREAMBOOK(4, "dreambook");

    private final Integer id;
    private final String name;

    ArticleCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArticleCategory getById(Integer id) {
        Optional<ArticleCategory> o = Arrays.stream(ArticleCategory.values()).filter(t -> t.id.equals(id)).findAny();
        return o.orElse(null);
    }
}
