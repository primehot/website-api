package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import web.api.domain.arcticle.woman.WomanArticleEntity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface WomanArticleRepository extends PagingAndSortingRepository<WomanArticleEntity, Long> {

    Optional<WomanArticleEntity> findFirstByOrderByCreationDateAsc();

    Collection<WomanArticleEntity> findTop10ByOrderByCreationDateAscTimesVisitedAsc();

    Collection<WomanArticleEntity> findTop4ByOrderByCreationDateAsc();

    @Query("SELECT n from WomanArticleEntity n where n.creationDate > :dateBefore order by n.timesVisited, n.creationDate")
    List<WomanArticleEntity> getRecommended(@Param("dateBefore") Timestamp dateBefore, Pageable pageable);

    @Query("SELECT a.image from WomanArticleEntity a where a.id = :articleId")
    Optional<Byte[]> findArticleImageById(@Param("articleId") long articleId);

    @Query("Select n from WomanArticleEntity n "
            + "where n.womanTopic = :topicId order by n.creationDate, n.timesVisited")
    Page<WomanArticleEntity> findAllByWomanTopic(@Param("topicId") Integer topicId,
                                                 Pageable pageable);

    @Query("SELECT DISTINCT n from WomanArticleEntity n "
            + " where n.hashTags LIKE %:wrappedHashTag% ")
    Page<WomanArticleEntity> findAllByHashTag(@Param("wrappedHashTag") String wrappedHashTag,
                                              Pageable pageable);
}
