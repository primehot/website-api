package web.api.domain.arcticle;

/**
 * Created by oleht on 12.10.2018
 */
public interface ArticleRankedProjection {

    Long getId();

    String getTitle();

    String getTopic();

    String getHotContent();

    String getContent();

    String getHashTags();

    Float getRank();
}
