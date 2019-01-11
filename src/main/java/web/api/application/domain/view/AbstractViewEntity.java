package web.api.application.domain.view;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by oleht on 12.10.2018
 */
@MappedSuperclass
@Setter
@Getter
public abstract class AbstractViewEntity<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    private String title;

    private String hotContent;

}
