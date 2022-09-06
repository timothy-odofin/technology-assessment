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
@Table(name = "transactions")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transactions extends BaseEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "tranCode", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID tranCode;
    private Integer quantityPurchased;
    private Double unitPrice;
    private Double discount;
    private String discountType;
    private Double netAmount;
    private Double amount;
    private String description;
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @ManyToOne
    private Buyer buyer;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne
    private StoreItem item;


}
