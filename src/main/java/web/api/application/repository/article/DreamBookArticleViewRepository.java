package web.api.application.repository.article;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import web.api.application.domain.projection.IdProjection;
import web.api.application.domain.view.DreamBookArticleViewEntity;

import java.util.Optional;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookArticleViewRepository extends CrudRepository<DreamBookArticleViewEntity, Long> {

    @Query("SELECT d.id as id FROM DreamBookArticleViewEntity d where d.dreamBookId = :dreamBookId")
    Optional<IdProjection> getIdByDreamBookId(@Param("dreamBookId") Long dreamBookId);

}
