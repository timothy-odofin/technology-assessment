package technology.assessment.app.model.dto.request;

import lombok.Data;

@Data
public class StoreItemCategoryRequest {
    private String categoryName;
    private String description;
    private String postedByUser;
}
