package web.api.application.repository.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.application.domain.projection.ImageProjection;
import web.api.application.domain.projection.ArticleRankedProjection;
import web.api.application.domain.entity.arcticle.woman.WomanArticleEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface WomanArticleRepository extends PagingAndSortingRepository<WomanArticleEntity, Long> {

//    @Query("SELECT a.mainImage as image from WomanArticleEntity a where a.id = :articleId")
//    Optional<ImageProjection> findArticleImageById(@Param("articleId") long articleId);

    @Query("Select n from WomanArticleEntity n "
            + "where n.womanTopic = :topicId order by n.creationDate, n.timesVisited")
    Page<WomanArticleEntity> findAllByWomanTopic(@Param("topicId") Integer topicId,
                                                 Pageable pageable);

    @Query("SELECT DISTINCT n from WomanArticleEntity n "
            + " where n.hashTags LIKE %:wrappedHashTag% ")
    Page<WomanArticleEntity> findAllByHashTag(@Param("wrappedHashTag") String wrappedHashTag,
                                              Pageable pageable);

    @Query(
            value = "SELECT wa.id AS id, " +
                    " wa.title AS title, " +
                    " wa.woman_topic AS topic, " +
                    " wa.hot_content AS hotContent, " +
                    " wa.hash_tags AS hashTags, " +
                    " ts_rank_cd(wa.document_tokens, q) AS rank " +
                    "  FROM woman_article AS wa, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE wa.document_tokens @@ q" +
                    "  ORDER BY rank DESC, wa.times_visited DESC",
            countQuery = "SELECT count(*) " +
                    "  FROM woman_article AS wa, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE wa.document_tokens @@ q",
            nativeQuery = true)
    Page<ArticleRankedProjection> getByPhrase(String formattedPhrase, Pageable pageable);
}
