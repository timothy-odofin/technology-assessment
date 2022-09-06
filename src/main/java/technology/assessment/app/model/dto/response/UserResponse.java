package technology.assessment.app.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.enums.AccountType;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseDto{
    private String firstName;
    private String lastName;
    private AccountType userCategory;
    private UUID userToken;
}
