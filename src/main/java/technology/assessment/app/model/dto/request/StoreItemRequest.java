package technology.assessment.app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static technology.assessment.app.util.MessageUtil.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreItemRequest {
    @NotBlank(message = ITEM_REQUIRED)
    private String itemName;
    private String itemCode;
    @NotBlank(message = DESCRIPTION_REQUIRED)
    private String description;
    @NotBlank(message = USER_REQUIRED)
    private String postedByUser;
    @NotBlank(message = CATEGORY_REQUIRED)
    private String categoryCode;
    @NotNull(message = QUANTITY_REQUIRED)
    @Min(value=1, message = QUANTITY_REQUIRED)
    private Integer quantity;
    @Min(value=1, message = PRICE_REQUIRED)
    private Double price;
}
