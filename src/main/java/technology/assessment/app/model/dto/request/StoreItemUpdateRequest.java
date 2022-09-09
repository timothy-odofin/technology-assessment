package technology.assessment.app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static technology.assessment.app.util.MessageUtil.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemUpdateRequest {
    @NotBlank(message = ITEM_REQUIRED)
    private String itemCode;
    @NotBlank(message = USER_REQUIRED)
    private String postedByUser;
    @NotNull(message = QUANTITY_REQUIRED)
    @Min(value = 1, message = QUANTITY_REQUIRED)
    private Integer quantity;
    private Double price;

}
