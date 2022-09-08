package technology.assessment.app.model;

import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.model.enums.AccountType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class DataUtils {

    public static List<Users> testUsers(){
        Users user =Users.builder()
                .userToken("dkekP3Orb")
                .lastName("ODOFIN")
                .firstName("OYEJIDE")
                .userCategory(AccountType.EMPLOYEE.name())
                .registeredDate(LocalDate.now())
                .registeredDate(LocalDate.now())
                .build();
        user.setDateCreated(LocalDateTime.now());
        user.setLastModified(LocalDateTime.now());
        user.setId(Long.valueOf("4"));
        return Collections.singletonList(user);

    }
    public static UserRequest testUser(){
        return UserRequest.builder()
                .userCategory(AccountType.CUSTOMER)
                .firstName("OYEJIDE")
                .lastName("ODOFIN")
                .build();

    }
}
