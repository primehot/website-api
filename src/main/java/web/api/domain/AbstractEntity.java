package web.api.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Created by oleht on 12.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private T id;

    @CreationTimestamp
    private Timestamp creationDate;

    @UpdateTimestamp
    private Timestamp lastModificationDate;

    private Long timesVisited = 0L;

}
