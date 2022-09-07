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

    private String code;
    private String categoryName;
    private String description;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private Users createdBy;


}
