package web.api.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.application.domain.arcticle.ArticleRankedProjection;
import web.api.application.domain.arcticle.ImageProjection;
import web.api.application.domain.arcticle.news.NewsArticleEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface NewsArticleRepository extends PagingAndSortingRepository<NewsArticleEntity, Long> {

    @Query("SELECT n from NewsArticleEntity n where n.creationDate > :dateBefore order by n.timesVisited, n.creationDate")
    List<NewsArticleEntity> getRecommended(@Param("dateBefore") Timestamp dateBefore, Pageable pageable);

    @Query("SELECT a.mainImage as image from NewsArticleEntity a where a.id = :articleId")
    Optional<ImageProjection> findArticleImageById(@Param("articleId") long articleId);

    @Query("SELECT n from NewsArticleEntity n "
            + "where n.newsTopic = :topicId")
    Page<NewsArticleEntity> findAllByNewsTopic(@Param("topicId") Integer topicId,
                                               Pageable pageable);

    @Query("SELECT DISTINCT n from NewsArticleEntity n "
            + " where n.hashTags LIKE %:wrappedHashTag% ")
    Page<NewsArticleEntity> findAllByHashTag(@Param("wrappedHashTag") String wrappedHashTag,
                                             Pageable pageable);

    @Query(
            value = "SELECT na.id AS id, " +
                    " na.title AS title, " +
                    " na.news_topic AS topic, " +
                    " na.hot_content AS hotContent, " +
                    " na.hash_tags AS hashTags, " +
                    " ts_rank_cd(na.document_tokens, q) AS rank " +
                    "  FROM news_article AS na, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE na.document_tokens @@ q" +
                    "  ORDER BY rank DESC, na.times_visited DESC",
            countQuery = "SELECT count(*) " +
                    "  FROM news_article AS na, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE na.document_tokens @@ q",
            nativeQuery = true)
    Page<ArticleRankedProjection> getByPhrase(String formattedPhrase, Pageable pageable);
}
