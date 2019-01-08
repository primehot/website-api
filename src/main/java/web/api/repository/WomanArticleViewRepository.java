package web.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import web.api.domain.arcticle.IdProjection;
import web.api.domain.view.WomanArticleViewEntity;

import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface WomanArticleViewRepository extends CrudRepository<WomanArticleViewEntity, Long> {

    @Query("SELECT w.id as id FROM WomanArticleViewEntity w where w.womanId = :womanId")
    Optional<IdProjection> getIdByWomanId(@Param("womanId") Long womanId);

}
