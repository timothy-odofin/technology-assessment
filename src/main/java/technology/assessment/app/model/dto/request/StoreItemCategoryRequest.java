package technology.assessment.app.model.dto.request;

import lombok.Data;
import technology.assessment.app.util.MessageUtil;

import javax.validation.constraints.NotBlank;

import static technology.assessment.app.util.MessageUtil.*;

@Data
public class StoreItemCategoryRequest {
    @NotBlank(message = NAME_REQUIRED)
    private String categoryName;
    @NotBlank(message = DESCRIPTION_REQUIRED)
    private String description;
    @NotBlank(message = USER_REQUIRED)
    private String postedByUser;
}
