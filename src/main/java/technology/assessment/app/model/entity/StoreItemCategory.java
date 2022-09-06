package technology.assessment.app.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "store_item_category")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemCategory extends BaseEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "code", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID code;
    private String categoryName;
    private String description;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Users createdBy;


}
