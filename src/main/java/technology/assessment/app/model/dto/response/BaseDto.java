package technology.assessment.app.model.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
    protected Date dateCreated;
    protected Date lastModified;
}
