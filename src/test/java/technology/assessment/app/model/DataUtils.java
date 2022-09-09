package technology.assessment.app.model;

import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.request.StoreItemUpdateRequest;
import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.model.dto.response.helpers.CategoryHelper;
import technology.assessment.app.model.dto.response.helpers.UserHelper;
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
import static technology.assessment.app.util.MessageUtil.*;

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
        return new ApiResponse<>(SUCCESS,CREATED, DONE);
    }
    public static ApiResponse<String> updateResponse(){
        return new ApiResponse<>(SUCCESS,OKAY, UPDATED);
    }
    public static StoreItemUpdateRequest testStoreItemUpdateRequest(){
        return StoreItemUpdateRequest.builder()
                .itemCode("test")
                .categoryCode("test")
                .postedByUser("test")
                .quantity(39)
                .build();

    }
    public static UserRequest testUser(){
        return UserRequest.builder()
                .userCategory(AccountType.CUSTOMER)
                .firstName("OYEJIDE")
                .lastName("ODOFIN")
                .build();

    }
    public static String testStoreItemCategoryRequestError(){
        return String.join(",",Arrays.asList(NAME_REQUIRED,DESCRIPTION_REQUIRED,USER_REQUIRED));

    }
    public static String testStoreItemUpdateRequestError(){
        return String.join(",",Arrays.asList(ITEM_REQUIRED,USER_REQUIRED,QUANTITY_REQUIRED));

    }
    public static StoreItemCategoryRequest testStoreItemCategoryRequest(){

        return StoreItemCategoryRequest.builder()
                .categoryName("Test 1")
                .description("Test 1")
                .postedByUser(testUsers().get(0).getUserToken())
                .build();
    }

    public static StoreItemRequest testStoreItemRequest(){

        return StoreItemRequest.builder()
                .itemName("Test 1")
                .description("Test 1")
                .description("Test")
                .categoryCode("12345")
                .postedByUser("123")
                .quantity(2)
                .price(500.0)
                .postedByUser(testUsers().get(0).getUserToken())
                .build();
    }
    public static String testStoreItemRequestError(){
        return String.join(",",Arrays.asList(ITEM_REQUIRED,DESCRIPTION_REQUIRED,USER_REQUIRED,CATEGORY_REQUIRED,QUANTITY_REQUIRED,PRICE_REQUIRED));
    }
    public static ApiResponse<List<StoreItemResponse>> testStoreItemResponseList(){
       return new ApiResponse<>(SUCCESS,OKAY,Collections.singletonList(
               StoreItemResponse.builder()
                       .category(testCategoryHelper())
                       .code("123")
                       .createdBy(testUserHelper())
                       .itemName("Test")
                       .description("test desc")
                       .price(200.0)
                       .quantity(30)
                       .build()
       ));
    }
    public static ApiResponse<List<StoreItemCategoryResponse>> testStoreItemCategoryResponseData(){
        return new ApiResponse<>(SUCCESS,OKAY,Collections.singletonList(
                StoreItemCategoryResponse.builder()
                        .code("123")
                        .createdBy(testUserHelper())
                        .description("test desc")
                        .categoryName("Test")
                        .build()
        ));
    }
    public static UserHelper testUserHelper(){
        return UserHelper.builder()
                .firstName("Oyejide")
                .lastName("Odofin")
                .userCategory(AccountType.CUSTOMER)
                .userToken("123")
                .build();
    }
    public static CategoryHelper testCategoryHelper(){
       return CategoryHelper.builder()
               .categoryName("Test")
               .code("1233")
               .build();
    }
}
