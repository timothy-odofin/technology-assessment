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
@Table(name = "store_item")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreItem extends BaseEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "code", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID code;
    private String itemName;
    private String description;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Users createdBy;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private StoreItemCategory category;
    private Integer quantity;
    private Double price;


}
