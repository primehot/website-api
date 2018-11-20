package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.api.domain.dream_book.DreamBookEntity;

import java.util.Collection;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookRepository extends PagingAndSortingRepository<DreamBookEntity, Long> {

    Collection<DreamBookEntity> findAllByTitleOrderByCreationDateAscTimesVisitedAsc(String title);

    @Query("SELECT d.title from DreamBookEntity as d order by d.timesVisited asc ")
    Page<String> findMainTitles(Pageable pageable);

    @Query("SELECT d from DreamBookEntity as d order by d.timesVisited asc ")
    Page<DreamBookEntity> findTopVisited(Pageable pageable);
}
