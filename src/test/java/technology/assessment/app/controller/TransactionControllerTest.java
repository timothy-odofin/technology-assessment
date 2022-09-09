package technology.assessment.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import technology.assessment.app.exception.ItemOutOfStockException;
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.TransactionResponse;
import technology.assessment.app.service.TransactionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.TransactionEndpoints.*;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;

@WebMvcTest(TransactionController.class)
@Slf4j
class TransactionControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void test_purchase_item_success() throws Exception {
        when(transactionService.purchaseItem(any())).thenReturn(DataUtils.testPurchaseItemResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testTransactionRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),TRANSACTION_PROCESSED_SUCCESSFULLY);
    }

    @Test
    void test_purchase_item_invalid_user() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(USER_REQUIRED))
                .when(transactionService).purchaseItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testTransactionRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),USER_REQUIRED);
    }

    @Test
    void test_purchase_item_invalid_item() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(ITEM_REQUIRED))
                .when(transactionService).purchaseItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testTransactionRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),ITEM_REQUIRED);
    }
    @Test
    void test_purchase_item_out_of_stock() throws Exception {
        Mockito.doThrow(new ItemOutOfStockException(OUT_OF_STOCK))
                .when(transactionService).purchaseItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testTransactionRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), INSSUFICIENT);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),OUT_OF_STOCK);
    }
    @Test
    void test_list_transaction_by_item_code_success() throws Exception {
        when(transactionService.listTransactionByItemCode(any())).thenReturn(DataUtils.testTransactionResponseData());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST+"?itemCode=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<TransactionResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);

    }
    @Test
    void test_list_transaction_by_item_code_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(transactionService).listTransactionByItemCode(any());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST+"?itemCode=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);

    }

    @Test
    void test_list_transaction_by_user_token_success() throws Exception {
        when(transactionService.listTransactionByUser(any())).thenReturn(DataUtils.testTransactionResponseData());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_BY_BUYER+"?userToken=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<TransactionResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);

    }
    @Test
    void test_list_transaction_by_user_token_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(transactionService).listTransactionByUser(any());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_BY_BUYER+"?userToken=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);

    }

}
