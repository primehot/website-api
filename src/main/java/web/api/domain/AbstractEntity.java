package web.api.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by oleht on 12.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @UpdateTimestamp
    private Timestamp lastModificationDate;

    private Long timesVisited = 0L;

}
