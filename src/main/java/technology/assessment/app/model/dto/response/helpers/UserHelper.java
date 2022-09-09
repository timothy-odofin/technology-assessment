package technology.assessment.app.model.dto.response.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.enums.AccountType;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHelper {
    private String firstName;
    private String lastName;
    private AccountType userCategory;
    private String userToken;
}
