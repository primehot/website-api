package web.api.application.dto.unit.article_draft;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 14.01.2019
 */
public enum ParagraphType {
    NO_IMAGE, LEFT_IMAGE, TOP_IMAGE, RIGHT_IMAGE, BOTTOM_IMAGE;

    public static ParagraphType getByName(String name) {
        Optional<ParagraphType> o = Arrays.stream(ParagraphType.values()).filter(t -> t.toString().equals(name)).findAny();
        return o.orElse(null);
    }
}
