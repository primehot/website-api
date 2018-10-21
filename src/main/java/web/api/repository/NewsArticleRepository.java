package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.domain.arcticle.news.NewsArticleEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface NewsArticleRepository extends PagingAndSortingRepository<NewsArticleEntity, Long> {

    Optional<NewsArticleEntity> findFirstByOrderByCreationDateAscTimesVisitedAsc();

    @Query("SELECT n from NewsArticleEntity n where n.creationDate > :dateBefore order by n.timesVisited, n.creationDate")
    List<NewsArticleEntity> getRecommended(@Param("dateBefore") Timestamp dateBefore, Pageable pageable);

    Collection<NewsArticleEntity> findTop10ByOrderByCreationDateAscTimesVisitedAsc();

    @Query("SELECT a.image from NewsArticleEntity a where a.id = :articleId")
    Optional<Byte[]> findArticleImageById(@Param("articleId") long articleId);

    @Query("Select n from NewsArticleEntity n "
            + "where n.newsTopic = :topicId order by n.creationDate, n.timesVisited")
    Page<NewsArticleEntity> findAllByNewsTopic(@Param("topicId") Integer topicId,
                                               Pageable pageable);
}
