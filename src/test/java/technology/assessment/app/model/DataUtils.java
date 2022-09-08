package technology.assessment.app.model;

import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.model.enums.AccountType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static technology.assessment.app.util.AppCode.CREATED;
import static technology.assessment.app.util.AppCode.OKAY;
import static technology.assessment.app.util.MessageUtil.SUCCESS;
import static technology.assessment.app.util.MessageUtil.USER_CREATED;

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
    public static ApiResponse<List<UserResponse>> testUsersResponse(){
        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertList(testUsers(),UserResponse.class));

    }
    public static List<String> userError(){
        return new ArrayList<>(Arrays.asList("firstName: First name is required","lastName: Last name is required"));

    }
    public static ApiResponse<String> createUserResponse(){
        return new ApiResponse<>(SUCCESS,CREATED, USER_CREATED);
    }
    public static UserRequest testUser(){
        return UserRequest.builder()
                .userCategory(AccountType.CUSTOMER)
                .firstName("OYEJIDE")
                .lastName("ODOFIN")
                .build();

    }
}
