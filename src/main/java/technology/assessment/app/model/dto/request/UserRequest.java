package technology.assessment.app.model.dto.request;

import lombok.Data;
import technology.assessment.app.model.enums.AccountType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import static technology.assessment.app.util.MessageUtil.FIRST_NAME_REQUIRED;
import static technology.assessment.app.util.MessageUtil.LAST_NAME_REQUIRED;

@Data
public class UserRequest {
    @NotBlank(message = FIRST_NAME_REQUIRED)
    private String firstName;
    @NotBlank(message = LAST_NAME_REQUIRED)
    private String lastName;
    private AccountType userCategory;
    private LocalDate creationDate;
}
