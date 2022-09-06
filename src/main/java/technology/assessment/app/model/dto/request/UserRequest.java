package technology.assessment.app.model.dto.request;

import lombok.Data;
import technology.assessment.app.model.enums.AccountType;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private AccountType userCategory;
}
