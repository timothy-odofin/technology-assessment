package technology.assessment.app.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static technology.assessment.app.util.MessageUtil.*;

@Data
public class TransactionRequest {
    @NotBlank(message = USER_REQUIRED)
    String buyerToken;
    @NotBlank(message = ITEM_REQUIRED)
    String itemCode;
    @NotBlank(message = QUANTITY_REQUIRED)
    Integer quantity;
}
