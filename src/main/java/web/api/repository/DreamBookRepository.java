package web.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.api.domain.dream_book.DreamBookEntity;
import web.api.domain.dream_book.DreamBookRankedProjection;

import java.util.Collection;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookRepository extends PagingAndSortingRepository<DreamBookEntity, Long> {

    Collection<DreamBookEntity> findAllByTitleOrderByCreationDateAscTimesVisitedAsc(String title);

    @Query("SELECT d.title, sum(d.timesVisited) as f from DreamBookEntity as d group by title order by f desc ")
    Page<Object[]> findMainTitles(Pageable pageable);

    @Query("SELECT d from DreamBookEntity as d order by d.timesVisited desc ")
    Page<DreamBookEntity> findTopVisited(Pageable pageable);

    @Query(
            value = "SELECT ts_headline(db.title, q, 'StartSel=<b>, StopSel=</b>') AS title, " +
                    " ts_headline(db.content, q, 'StartSel=<b>, StopSel=</b>') AS content, " +
                    " db.author AS author, " +
                    " ts_rank_cd(db.document_tokens, q) AS rank " +
                    "  FROM dream_book AS db, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE db.document_tokens @@ q" +
                    "  ORDER BY rank DESC, db.times_visited DESC",
            countQuery = "SELECT count(*) " +
                    "  FROM dream_book AS db, " +
                    " to_tsquery('russian', ?1) AS q" +
                    "  WHERE db.document_tokens @@ q",
            nativeQuery = true)
    Page<DreamBookRankedProjection> getByPhrase(String phrase, Pageable pageable);
}
