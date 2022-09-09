package technology.assessment.app.model.dto.response.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemHelper {
    private String code;
    private String itemName;
    private String description;
    private CategoryHelper category;
    private Double price;
}
