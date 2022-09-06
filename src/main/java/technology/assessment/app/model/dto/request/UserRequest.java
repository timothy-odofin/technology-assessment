package technology.assessment.app.model.dto.request;

import lombok.Data;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.validation.ValueOfEnum;

import javax.validation.constraints.NotBlank;

import static technology.assessment.app.util.MessageUtil.*;

@Data
public class UserRequest {
    @NotBlank(message = FIRST_NAME_REQUIRED)
    private String firstName;
    @NotBlank(message = LAST_NAME_REQUIRED)
    private String lastName;
    @ValueOfEnum(enumClass = AccountType.class)
    private String userCategory;
}
