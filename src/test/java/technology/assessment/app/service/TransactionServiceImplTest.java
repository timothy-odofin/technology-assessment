package technology.assessment.app.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.TransactionResponse;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.StoreItemCategory;
import technology.assessment.app.model.entity.Transactions;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.repository.StoreItemCategoryRepo;
import technology.assessment.app.repository.StoreItemRepo;
import technology.assessment.app.repository.TransactionsRepo;
import technology.assessment.app.repository.UsersRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.model.DataUtils.*;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.AppUtil.formatMessage;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.ParamName.USER_TOKEN;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;
import static technology.assessment.app.util.TransactionEndpoints.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Slf4j
class TransactionServiceImplTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private TransactionsRepo transactionsRepo;

    @Autowired
    private StoreItemRepo storeItemRepo;
    @Autowired
    private StoreItemCategoryRepo storeItemCategoryRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        storeItemRepo.deleteAllInBatch();
        storeItemCategoryRepo.deleteAllInBatch();
        usersRepo.deleteAllInBatch();
        transactionsRepo.deleteAllInBatch();
    }

    StoreItemCategory initCategory(StoreItemCategory category) {
        category.setCreatedBy(initUser(testCreateUser()));
        return storeItemCategoryRepo.save(category);

    }

    StoreItem initStoreItem(StoreItemCategory storeItemCategory) {
        StoreItemCategory category = initCategory(storeItemCategory);
        StoreItem item = DataUtils.testStoreItemData();
        item.setCategory(category);
        item.setCreatedBy(category.getCreatedBy());
        return storeItemRepo.save(item);

    }

    Users initUser(Users user) {
        return usersRepo.save(user);
    }

    Transactions initTransaction() {
        StoreItem item = initStoreItem(testCreateStoreItemCategory());
        Transactions transactions = testTransactionRequestData();
        transactions.setItem(item);
        transactions.setBuyer(item.getCreatedBy());
        transactions.setUnitPrice(item.getPrice());
        transactions.setQuantityPurchased(2);
        transactions.setAmount(item.getPrice() * transactions.getQuantityPurchased());
        return transactionsRepo.save(transactions);


    }

    @Test
    void test_purchase_item_success_get_no_percent_on_groceries() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory1());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(2);
        transactionRequest.setBuyerToken(item.getCreatedBy().getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),0.0);
        assertEquals(tr.getNetAmount(), item.getPrice()*transactionRequest.getQuantity());

    }

    @Test
    void test_purchase_item_success_get_30_percent_discount() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory2());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(100);
        Users user =initUser(testCreateUserV2());
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),1500.0);
        assertEquals(tr.getNetAmount(), tr.getAmount()-tr.getDiscount());

    }
    @Test
    void test_purchase_item_success_get_5_percent_discount() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory2());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(100);
        Users user =initUser(testCreateUser3());
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),250.0);
        assertEquals(tr.getNetAmount(), tr.getAmount()-tr.getDiscount());

    }
    @Test
    void test_purchase_item_success_get_10_percent_discount() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory2());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(100);
        Users user =initUser(testCreateUser5());
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),500.0);
        assertEquals(tr.getNetAmount(), tr.getAmount()-tr.getDiscount());

    }

    @Test
    void test_purchase_item_success_get_5_usd() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory2());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(100);
        Users user =initUser(testCreateUser4());
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),250.0);
        assertEquals(tr.getNetAmount(), tr.getAmount()-tr.getDiscount());

    }

    @Test
    void test_purchase_item_success_get_no_discount_bills_less_than_100() throws Exception {
        StoreItem item = initStoreItem(testStoreItemCategory2());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setQuantity(1);
        Users user =initUser(testCreateUser4());
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), TRANSACTION_PROCESSED_SUCCESSFULLY);
        List<Transactions> transactions = transactionsRepo.listByItemCode(item.getCode());
        assertEquals(transactions.size(),1);
        Transactions tr = transactions.get(0);
        assertEquals(tr.getDiscount(),0.0);
        assertEquals(tr.getNetAmount(), tr.getAmount());

    }

    @Test
    void test_purchase_item_invalid_buyer() throws Exception {
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setBuyerToken("123");
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), USER_REQUIRED);
    }

    @Test
    void test_purchase_item_invalid_item() throws Exception {
        Users user = initUser(testCreateUser());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setBuyerToken(user.getUserToken());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), ITEM_REQUIRED);
    }

    @Test
    void test_purchase_item_out_of_stock() throws Exception {
        Users user = initUser(testCreateUser());
        StoreItem item = initStoreItem(testCreateStoreItemCategory());
        TransactionRequest transactionRequest = DataUtils.testTransactionRequest();
        transactionRequest.setBuyerToken(user.getUserToken());
        transactionRequest.setItemCode(item.getCode());
        transactionRequest.setQuantity(5000);
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(transactionRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), INSSUFICIENT);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), OUT_OF_STOCK);
    }

    @Test
    void test_purchase_item_bad_request() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setQuantity(-20);
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(request)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 3);
    }

    @Test
    void test_list_transaction_by_item_code_success() throws Exception {
        Transactions transactions = initTransaction();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST + "?itemCode=" + transactions.getItem().getCode())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<TransactionResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);

    }

    @Test
    void test_list_transaction_by_item_code_failed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST + "?itemCode=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), ITEM_REQUIRED);

    }

    @Test
    void test_list_transaction_by_item_code_empty() throws Exception {
        StoreItem item = initStoreItem(testCreateStoreItemCategory());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST + "?itemCode=" + item.getCode())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), formatMessage(NO_TRANSACTION, ITEM));
    }

    @Test
    void test_list_transaction_by_user_token_success() throws Exception {
        Transactions transactions = initTransaction();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_BY_BUYER + "?userToken=" + transactions.getBuyer().getUserToken())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<TransactionResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);

    }

    @Test
    void test_list_transaction_by_user_token_failed() throws Exception {
        initTransaction();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_BY_BUYER + "?userToken=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), USER_REQUIRED);

    }

    @Test
    void test_list_transaction_by_user_token_empty() throws Exception {
        initTransaction();
        Users user = initUser(testCreateUser());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_BY_BUYER + "?userToken=" + user.getUserToken())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), formatMessage(NO_TRANSACTION, USER_TOKEN));

    }
}
