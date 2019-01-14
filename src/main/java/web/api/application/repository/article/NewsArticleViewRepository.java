package web.api.application.repository.article;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import web.api.application.domain.projection.IdProjection;
import web.api.application.domain.view.NewsArticleViewEntity;

import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface NewsArticleViewRepository extends CrudRepository<NewsArticleViewEntity, Long> {

    @Query("SELECT n.id as id FROM NewsArticleViewEntity n where n.newsId = :newsId")
    Optional<IdProjection> getIdByNewsId(@Param("newsId") Long newsId);

}
