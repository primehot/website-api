package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.domain.arcticle.news.NewsArticleEntity;
import web.api.domain.arcticle.woman.WomanArticleEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface WomanArticleRepository extends PagingAndSortingRepository<WomanArticleEntity, Long> {

    Collection<WomanArticleEntity> findByWomanTopic(Integer topic);

    Optional<WomanArticleEntity> findFirstByOrderByCreationDateAsc();

    Collection<WomanArticleEntity> findTop10ByOrderByCreationDateAsc();

    @Query("SELECT a.image from WomanArticleEntity a where a.id = :articleId")
    Optional<Byte[]> findArticleImageById(@Param("articleId") long articleId);

    @Query("Select n from WomanArticleEntity n "
            + "where n.womanTopic = :topicId order by n.creationDate")
    Page<WomanArticleEntity> findAllByWomanTopic(@Param("topicId") Integer topicId,
                                                 Pageable pageable);

}
