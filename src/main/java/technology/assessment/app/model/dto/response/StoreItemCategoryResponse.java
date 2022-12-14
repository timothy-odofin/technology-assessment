package technology.assessment.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.dto.response.helpers.UserHelper;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemCategoryResponse extends BaseDto {
    private String code;
    private String categoryName;
    private String description;
    private UserHelper createdBy;
}
