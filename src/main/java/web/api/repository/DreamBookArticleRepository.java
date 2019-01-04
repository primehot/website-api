package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.domain.arcticle.ArticleRankedProjection;
import web.api.domain.arcticle.ImageProjection;
import web.api.domain.arcticle.dream.DreamBookArticleEntity;

import java.util.Optional;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookArticleRepository extends PagingAndSortingRepository<DreamBookArticleEntity, Long> {

    @Query("SELECT a.mainImage as image from DreamBookArticleEntity a where a.id = :articleId")
    Optional<ImageProjection> findArticleImageById(@Param("articleId") long articleId);

    @Query("SELECT DISTINCT n from DreamBookArticleEntity n "
            + " where n.hashTags LIKE %:wrappedHashTag% ")
    Page<DreamBookArticleEntity> findAllByHashTag(@Param("wrappedHashTag") String wrappedHashTag,
                                                  Pageable pageable);

    @Query(
            value = "SELECT dba.id AS id, " +
                    " dba.title AS title, " +
                    " dba.news_topic AS topic, " +
                    " dba.hot_content AS hotContent, " +
                    " dba.hash_tags AS hashTags, " +
                    " ts_rank_cd(dba.document_tokens, q) AS rank " +
                    "  FROM news_article AS na, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE dba.document_tokens @@ q" +
                    "  ORDER BY rank DESC, dba.times_visited DESC",
            countQuery = "SELECT count(*) " +
                    "  FROM dream_book_article AS dba, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE dba.document_tokens @@ q",
            nativeQuery = true)
    Page<ArticleRankedProjection> getByPhrase(String formattedPhrase, Pageable pageable);
}
