package technology.assessment.app.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import technology.assessment.app.model.enums.AccountType;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "users")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity {
    private String userToken;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;
    private String userCategory;


}
