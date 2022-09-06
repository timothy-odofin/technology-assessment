package technology.assessment.app.model.dto.request;

import lombok.Data;

@Data
public class StoreItemRequest {
    private String itemCode;
    private String itemName;
    private String description;
    private String postedByUser;
    private String categoryCode;
    private Integer quantity;
    private Double price;
}
