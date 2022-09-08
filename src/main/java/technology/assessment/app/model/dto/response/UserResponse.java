package technology.assessment.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.assessment.app.model.enums.AccountType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseDto{
    private String firstName;
    private String lastName;
    private String userCategory;
    private String userToken;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate registeredDate;
}
