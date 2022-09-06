package technology.assessment.app.model.dto.response.helpers;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryHelper {
    private UUID code;
    private String categoryName;
}
