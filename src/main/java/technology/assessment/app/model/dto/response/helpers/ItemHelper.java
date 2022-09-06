package technology.assessment.app.model.dto.response.helpers;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemHelper {
    private UUID code;
    private String itemName;
    private String description;
    private CategoryHelper category;
    private Double price;
}
