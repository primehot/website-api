package web.api.application.repository.article_draft;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.application.domain.entity.article_draft.dream.DreamBookArticleDraftEntity;
import web.api.application.domain.projection.ImageProjection;

import java.util.Optional;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookArticleDraftRepository extends PagingAndSortingRepository<DreamBookArticleDraftEntity, Long> {

    @Query("SELECT a.mainImage as image from DreamBookArticleEntity a where a.id = :articleId")
    Optional<ImageProjection> findArticleImageById(@Param("articleId") long articleId);
}
