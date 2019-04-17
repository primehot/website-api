package web.api.application.dto.unit.article_draft;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by oleht on 15.10.2018
 */
@Getter
@Setter
@NoArgsConstructor
public class ParagraphDto {
    private Integer id;
    private String content;
    private ParagraphType type;

    public static ParagraphDto of(Integer id, String content, ParagraphType type) {
        ParagraphDto p = new ParagraphDto();
        p.setId(id);
        p.setContent(content);
        p.setType(type);
        return p;
    }
}
