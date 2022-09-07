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

    private String code;
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
