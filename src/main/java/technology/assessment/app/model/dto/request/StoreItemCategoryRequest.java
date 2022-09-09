package technology.assessment.app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.util.MessageUtil;

import javax.validation.constraints.NotBlank;

import static technology.assessment.app.util.MessageUtil.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemCategoryRequest {
    @NotBlank(message = NAME_REQUIRED)
    private String categoryName;
    @NotBlank(message = DESCRIPTION_REQUIRED)
    private String description;
    @NotBlank(message = USER_REQUIRED)
    private String postedByUser;
}
