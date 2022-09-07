package technology.assessment.app.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDto {
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    protected LocalDateTime dateCreated;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    protected LocalDateTime lastModified;
}
