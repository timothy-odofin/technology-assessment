package technology.assessment.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.dto.response.helpers.ItemHelper;
import technology.assessment.app.model.dto.response.helpers.UserHelper;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse extends BaseDto {
    private String tranRef;
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
