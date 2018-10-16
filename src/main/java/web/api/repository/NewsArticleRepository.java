package web.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.api.domain.arcticle.news.NewsArticleEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by oleht on 14.10.2018
 */
public interface NewsArticleRepository extends PagingAndSortingRepository<NewsArticleEntity, Long> {

    Collection<NewsArticleEntity> findByNewsTopic(Integer topic);

    Optional<NewsArticleEntity> findFirstByOrderByCreationDateAsc();

//    @Query("SELECT a.image from NewsArticleEntity a where a.id = :articleId")
//    Optional<Byte[]> findArticleImageById(@Param("articleId") long articleId);

}
