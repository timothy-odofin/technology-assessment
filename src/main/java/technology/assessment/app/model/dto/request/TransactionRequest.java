package technology.assessment.app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static technology.assessment.app.util.MessageUtil.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank(message = USER_REQUIRED)
    String buyerToken;
    @NotBlank(message = ITEM_REQUIRED)
    String itemCode;
    @Min(value=1, message = QUANTITY_REQUIRED)
    Integer quantity;
}
