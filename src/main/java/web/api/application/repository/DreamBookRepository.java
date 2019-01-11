package web.api.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.api.application.domain.dream_book.DreamBookEntity;
import web.api.application.domain.dream_book.DreamBookRankedProjection;
import web.api.application.domain.dream_book.DreamBookTitlesProjection;

import java.util.Collection;

/**
 * Created by oleht on 20.11.2018
 */
public interface DreamBookRepository extends PagingAndSortingRepository<DreamBookEntity, Long> {

    Collection<DreamBookEntity> findAllByTitle(String title, Sort sort);

    @Query("SELECT d.title as title, sum(d.timesVisited) as timesVisited from DreamBookEntity as d group by title order by timesVisited desc ")
    Page<DreamBookTitlesProjection> findMainTitles(Pageable pageable);

    @Query(
            value = "SELECT ts_headline('russian', db.title, q, 'StartSel=<b>, StopSel=</b>') AS title, " +
                    " ts_headline('russian', db.content, q, 'StartSel=<b>, StopSel=</b>') AS content, " +
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
    Page<DreamBookRankedProjection> getByPhrase(String formattedPhrase, Pageable pageable);
}
