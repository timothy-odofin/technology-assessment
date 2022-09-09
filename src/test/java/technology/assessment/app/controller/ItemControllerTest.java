package technology.assessment.app.controller;

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
import technology.assessment.app.exception.BadRequestException;
import technology.assessment.app.exception.RecordAccessException;
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.service.ItemService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.model.DataUtils.*;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.ItemEndPoints.*;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;

@WebMvcTest(ItemController.class)
@Slf4j
class ItemControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void test_add_item_category_success() throws Exception {
        when(itemService.addItemCategory(any())).thenReturn(DataUtils.createUserResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemCategoryRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),DONE);
    }

    @Test
    void test_add_item_category_unauthorized() throws Exception {
        Mockito.doThrow(new RecordAccessException(UNAUTHORIZE))
                .when(itemService).addItemCategory(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemCategoryRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),UNAUTHORIZE);
    }

    @Test
    void test_add_item_category_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(testStoreItemCategoryRequestError()))
                .when(itemService).addItemCategory(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemCategoryRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),testStoreItemCategoryRequestError());
    }

    @Test
    void test_add_item_success() throws Exception {
        when(itemService.addItem(any())).thenReturn(DataUtils.createUserResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),DONE);
    }
    @Test
    void test_add_item_unauthorized() throws Exception {
        Mockito.doThrow(new RecordAccessException(UNAUTHORIZE))
                .when(itemService).addItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),UNAUTHORIZE);
    }
    @Test
    void test_add_item_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(testStoreItemRequestError()))
                .when(itemService).addItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),testStoreItemRequestError());
    }
    @Test
    void test_update_item_success() throws Exception {
        when(itemService.updateItem(any())).thenReturn(DataUtils.updateResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemUpdateRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(),UPDATED);
    }
    @Test
    void test_update_item_unauthorized() throws Exception {
        Mockito.doThrow(new RecordAccessException(UNAUTHORIZE))
                .when(itemService).updateItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemUpdateRequestV1())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),UNAUTHORIZE);
    }

    @Test
    void test_update_item_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(testStoreItemUpdateRequestError()))
                .when(itemService).updateItem(any());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemUpdateRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),testStoreItemUpdateRequestError());
    }

    @Test
    void test_list_item_return_success() throws Exception {
        when(itemService.listItem(anyInt(),anyInt())).thenReturn(DataUtils.testStoreItemResponseList());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);

    }
    @Test
    void test_list_item_return_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(itemService).listItem(anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);
    }
    @Test
    void test_list_item_by_category_code_return_success() throws Exception {
        when(itemService.listItemByCategory(any())).thenReturn(DataUtils.testStoreItemResponseList());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_ITEM_BY_CATEGORY_CODE+"?code=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);

    }
    @Test
    void test_list_item_by_category_code_return_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(itemService).listItemByCategory(any());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_ITEM_BY_CATEGORY_CODE+"?code=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);

    }
    @Test
    void test_list_item_category_return_success() throws Exception {
        when(itemService.listItemCategory(anyInt(),anyInt())).thenReturn(DataUtils.testStoreItemCategoryResponseData());
        MvcResult mvcResult = mockMvc.perform(get(BASE + CATEGORY_LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemCategoryResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);
    }
    @Test
    void test_list_item_category_return_failed() throws Exception {
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(itemService).listItemCategory(anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + CATEGORY_LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);
    }
}
