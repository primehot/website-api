package web.api.application.dto.unit.article_draft;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by oleht on 14.01.2019
 */
public enum ParagraphType {
    NO_IMAGE(0), LEFT_IMAGE(1), TOP_IMAGE(2), RIGHT_IMAGE(3), BOTTOM_IMAGE(4);

    private int typeId;

    ParagraphType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public static ParagraphType getById(Integer id) {
        Optional<ParagraphType> o = Arrays.stream(ParagraphType.values()).filter(t -> t.getTypeId() == id).findAny();
        return o.orElse(null);
    }
}
