package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.api.domain.dream_book.DreamBookEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookRepository extends PagingAndSortingRepository<DreamBookEntity, Long> {

    Collection<DreamBookEntity> findAllByTitleOrderByCreationDateAscTimesVisitedAsc(String title);

    /**
     * select distinct county from Event event
     inner join event.county county
     order by county.county
     * @param pageable
     * @return
     */
    @Query("SELECT d.title, sum(d.timesVisited) as f from DreamBookEntity as d group by title order by f desc ")
    Page<Object[]> findMainTitles(Pageable pageable);

    @Query("SELECT d from DreamBookEntity as d order by d.timesVisited desc ")
    Page<DreamBookEntity> findTopVisited(Pageable pageable);
}
