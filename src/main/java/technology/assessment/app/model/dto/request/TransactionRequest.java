package technology.assessment.app.model.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = QUANTITY_REQUIRED)
    @Min(value=1, message = QUANTITY_REQUIRED)
    Integer quantity;
}
