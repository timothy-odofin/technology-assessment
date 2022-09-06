package technology.assessment.app.model.dto.response;

import lombok.Data;
import technology.assessment.app.model.dto.response.helpers.ItemHelper;
import technology.assessment.app.model.dto.response.helpers.UserHelper;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.Users;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
public class TransactionResponse extends BaseDto {
    private UUID tranRef;
    private Integer quantityPurchased;
    private Double unitPrice;
    private Double discount;
    private String discountType;
    private Double netAmount;
    private Double amount;
    private String description;
    private UserHelper buyer;
    private ItemHelper item;
}
