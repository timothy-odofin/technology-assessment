package technology.assessment.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.dto.response.helpers.CategoryHelper;
import technology.assessment.app.model.dto.response.helpers.UserHelper;
import technology.assessment.app.model.entity.StoreItemCategory;
import technology.assessment.app.model.entity.Users;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemResponse extends BaseDto{
    private String code;
    private String itemName;
    private String description;
    private UserHelper createdBy;
    private CategoryHelper category;
    private Integer quantity;
    private Double price;
}
