package web.api.repository;

import web.api.domain.dream_book.DreamBookEntity;

import java.util.Collection;

/**
 * Created by oleht on 07.12.2018
 */
public interface DreamBookRepositoryELK {

    Collection<DreamBookEntity> findByFTS(String phrase);

}
