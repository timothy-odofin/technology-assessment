package technology.assessment.app.model.entity;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    protected Long id;

    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    protected Date dateCreated;

    @UpdateTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    protected Date lastModified;
}
