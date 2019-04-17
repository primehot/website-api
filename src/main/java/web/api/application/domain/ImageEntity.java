package web.api.application.domain;

import lombok.Getter;
import lombok.Setter;
import web.api.application.domain.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by oleht on 21.01.2019
 */
@Entity
@Table(name = "image")
@Getter
@Setter
public class ImageEntity extends AbstractEntity<Long> {

    private Integer paragraph;
    private Byte[] image;

}
