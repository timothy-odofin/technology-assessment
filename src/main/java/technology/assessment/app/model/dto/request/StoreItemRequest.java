package technology.assessment.app.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static technology.assessment.app.util.MessageUtil.*;

@Data
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
