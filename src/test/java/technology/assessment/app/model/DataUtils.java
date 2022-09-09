package technology.assessment.app.model;

import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.*;
import technology.assessment.app.model.dto.response.*;
import technology.assessment.app.model.dto.response.helpers.CategoryHelper;
import technology.assessment.app.model.dto.response.helpers.ItemHelper;
import technology.assessment.app.model.dto.response.helpers.UserHelper;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.StoreItemCategory;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.util.CodeUtil;
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
    public static Users testCreateUser(){
       return Users.builder()
                .userToken(CodeUtil.generateCode())
                .lastName("ODOFIN")
                .firstName("OYEJIDE")
                .userCategory(AccountType.EMPLOYEE.name())
                .registeredDate(LocalDate.now())
                .registeredDate(LocalDate.now())
                .build();

    }
    public static Users testCustomerUser(){
        return Users.builder()
                .userToken(CodeUtil.generateCode())
                .lastName("ODOFIN")
                .firstName("OYEJIDE")
                .userCategory(AccountType.CUSTOMER.name())
                .registeredDate(LocalDate.now())
                .registeredDate(LocalDate.now())
                .build();

    }
    public static StoreItemCategory testCreateStoreItemCategory(){
        return StoreItemCategory.builder()
                .categoryName("Test 1")
                .description("Test description")
                .code(CodeUtil.generateCode())
                .build();

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
                .quantity(10)
                .postedByUser("123")
                .itemCode("test")
                .build();
    }
    public static StoreItemUpdateRequest testStoreItemUpdateRequestV1(){
        return StoreItemUpdateRequest.builder()
                .quantity(10)
                .itemCode("Test")
                .postedByUser("123")
                .build();
    }
    public static UserRequest testUser(){
        return UserRequest.builder()
                .userCategory(AccountType.CUSTOMER)
                .firstName("OYEJIDE")
                .lastName("ODOFIN")
                .build();

    }
    public static UserRequest testBadUser(){
        return UserRequest.builder()
                .userCategory(AccountType.CUSTOMER)
                .firstName("")
                .lastName("")
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
                .postedByUser("123")
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
                .build();
    }
    public static StoreItemRequest testStoreItemBadRequest(){

        return StoreItemRequest.builder()
                .itemName("")
                .description("Test 1")
                .categoryCode("12345")
                .postedByUser("123")
                .price(500.0)
                .build();
    }
    public static StoreItem testStoreItemData(){
        return StoreItem.builder()
                .itemName("Rice")
                .description("Rice flower")
                .price(200.0)
                .code(CodeUtil.generateCode())
                .quantity(2000)
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
    public static ApiResponse<List<TransactionResponse>> testTransactionResponseData(){
        return new ApiResponse<>(SUCCESS,OKAY,Collections.singletonList(
                TransactionResponse.builder()
                        .tranRef("123")
                        .amount(300.0)
                        .buyer(testUserHelper())
                        .discount(100.0)
                        .discountType(AccountType.EMPLOYEE.name())
                        .item(testItemHelper())
                        .netAmount(200.0)
                        .description("test desc")
                        .quantityPurchased(1)
                        .unitPrice(300.0)
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
    public static ItemHelper testItemHelper(){
        return ItemHelper.builder()
                .category(testCategoryHelper())
                .code("123")
                .itemName("Test")
                .description("Test")
                .price(50.0)
                .build();

    }
    public static TransactionRequest testTransactionRequest(){
        return TransactionRequest.builder()
                .buyerToken("123")
                .itemCode("123")
                .quantity(10)
                .build();
    }
    public static ApiResponse<String> testPurchaseItemResponse(){
        return new ApiResponse<>(SUCCESS, OKAY, TRANSACTION_PROCESSED_SUCCESSFULLY);

    }
}
