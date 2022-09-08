package technology.assessment.app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.validation.EnumAccountTypePattern;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import static technology.assessment.app.util.MessageUtil.FIRST_NAME_REQUIRED;
import static technology.assessment.app.util.MessageUtil.LAST_NAME_REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = FIRST_NAME_REQUIRED)
    private String firstName;
    @NotBlank(message = LAST_NAME_REQUIRED)
    private String lastName;
    @EnumAccountTypePattern(regexp = "EMPLOYEE|CUSTOMER|AFFILIATE")
    private AccountType userCategory;
    private LocalDate creationDate;
}
