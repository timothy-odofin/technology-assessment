package technology.assessment.app.model.dto.response.helpers;

import lombok.Data;
import technology.assessment.app.model.enums.AccountType;

import java.util.UUID;
@Data
public class UserHelper {
    private String firstName;
    private String lastName;
    private AccountType userCategory;
    private UUID userToken;
}
