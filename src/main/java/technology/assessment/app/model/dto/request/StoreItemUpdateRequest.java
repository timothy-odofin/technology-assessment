package technology.assessment.app.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static technology.assessment.app.util.MessageUtil.*;

@Data
public class StoreItemUpdateRequest {
    private String itemCode;
    @NotBlank(message = USER_REQUIRED)
    private String postedByUser;
    @NotNull(message = QUANTITY_REQUIRED)
    @Min(value = 1, message = QUANTITY_REQUIRED)
    private Integer quantity;
    private Double price;
    private String categoryCode;
}
