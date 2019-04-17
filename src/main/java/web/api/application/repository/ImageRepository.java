package web.api.application.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import web.api.application.domain.ImageEntity;

/**
 * Created by oleht on 21.01.2019
 */
public interface ImageRepository extends PagingAndSortingRepository<ImageEntity, Long> {
}
