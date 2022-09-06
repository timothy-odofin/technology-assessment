package technology.assessment.app.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import technology.assessment.app.model.enums.AccountType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.UUID;

/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "buyer")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Buyer extends BaseEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_token", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID userToken;
    private String firstName;
    private String lastName;
    private AccountType userCategory;


}
