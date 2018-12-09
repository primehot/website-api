package web.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.api.domain.dream_book.DreamBookEntity;

import java.util.Collection;

/**
 * Created by oleht on 07.12.2018
 */
@Repository
public class DreamBookRepositoryELKImpl implements DreamBookRepositoryELK {

    @Override
    public Collection<DreamBookEntity> findByFTS(String phrase) {
        return null;
    }
}
