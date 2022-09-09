package technology.assessment.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.request.StoreItemUpdateRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.StoreItemCategory;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.repository.StoreItemCategoryRepo;
import technology.assessment.app.repository.StoreItemRepo;
import technology.assessment.app.repository.UsersRepo;
import technology.assessment.app.util.BaseIT;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.model.DataUtils.testCreateUser;
import static technology.assessment.app.model.DataUtils.testCustomerUser;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.ItemEndPoints.*;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;
class ItemServiceImplTest extends BaseIT {
    @Autowired
    private StoreItemRepo storeItemRepo;
    @Autowired
    private StoreItemCategoryRepo storeItemCategoryRepo;
    @Autowired
    private UsersRepo usersRepo;

    @BeforeEach
    void setUp() {
        storeItemRepo.deleteAllInBatch();
        storeItemCategoryRepo.deleteAllInBatch();
        usersRepo.deleteAllInBatch();
    }

    StoreItemCategory initCategory() {
        StoreItemCategory storeItemCategoryRequest = DataUtils.testCreateStoreItemCategory();
        storeItemCategoryRequest.setCreatedBy(initUser(testCreateUser()));
        return storeItemCategoryRepo.save(storeItemCategoryRequest);

    }
    StoreItem initStoreItem() {
        StoreItemCategory storeItemCategory = initCategory();
        StoreItem item = DataUtils.testStoreItemData();
        item.setCategory(storeItemCategory);
        item.setCreatedBy(storeItemCategory.getCreatedBy());
        return storeItemRepo.save(item);

    }

    Users initUser(Users user) {
        return usersRepo.save(user);
    }

    @Test
    void test_add_item_category_success() throws Exception {
        StoreItemCategoryRequest payload = DataUtils.testStoreItemCategoryRequest();
        payload.setPostedByUser(initUser(testCreateUser()).getUserToken());
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), DONE);
    }
    @Test
    void test_add_item_category_unauthorized() throws Exception {
        StoreItemCategoryRequest payload = DataUtils.testStoreItemCategoryRequest();
        payload.setPostedByUser(initUser(testCustomerUser()).getUserToken());
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), UNAUTHORIZE);
    }

    @Test
    void test_add_item_category_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new StoreItemCategoryRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 3);
    }

    @Test
    void test_add_item_category_user_not_found_request() throws Exception {
        StoreItemCategoryRequest payload = DataUtils.testStoreItemCategoryRequest();
        payload.setPostedByUser("123");
        MvcResult mvcResult = mockMvc.perform(post(BASE + CATEGORY_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), USER_REQUIRED);
    }

    @Test
    void test_add_item_success() throws Exception {
        StoreItemCategory storeItemCategory = initCategory();
        StoreItemRequest storeItemRequest = DataUtils.testStoreItemRequest();
        storeItemRequest.setPostedByUser(storeItemCategory.getCreatedBy().getUserToken());
        storeItemRequest.setCategoryCode(storeItemCategory.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(storeItemRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), DONE);
    }

    @Test
    void test_add_item_unauthorized() throws Exception {
        StoreItemCategory storeItemCategory = initCategory();
        StoreItemRequest storeItemRequest = DataUtils.testStoreItemRequest();
        storeItemRequest.setPostedByUser(initUser(testCustomerUser()).getUserToken());
        storeItemRequest.setCategoryCode(storeItemCategory.getCode());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(storeItemRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), UNAUTHORIZE);
    }

    @Test
    void test_add_item_invalid_category_code() throws Exception {
        StoreItemRequest storeItemRequest = DataUtils.testStoreItemRequest();
        storeItemRequest.setPostedByUser(initUser(testCreateUser()).getUserToken());
        storeItemRequest.setCategoryCode("123");
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(storeItemRequest)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), ITEM_REQUIRED);
    }

    @Test
    void test_add_item_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testStoreItemBadRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 2);
    }

    @Test
    void test_update_item_success() throws Exception {
        StoreItem item = initStoreItem();
        StoreItemUpdateRequest payload = DataUtils.testStoreItemUpdateRequest();
        payload.setItemCode(item.getCode());
        payload.setPostedByUser(item.getCategory().getCreatedBy().getUserToken());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), UPDATED);
        StoreItem updatedItem = storeItemRepo.checkExistence(item.getCode()).get(0);
        assertEquals(updatedItem.getQuantity(), item.getQuantity() + payload.getQuantity());
    }

    @Test
    void test_update_item_unauthorized() throws Exception {
        StoreItem item = initStoreItem();
        StoreItemUpdateRequest payload = DataUtils.testStoreItemUpdateRequest();
        payload.setItemCode(item.getCode());
        payload.setPostedByUser(initUser(testCustomerUser()).getUserToken());
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), UNAUTHORIZE_CODE);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), UNAUTHORIZE);
    }

    @Test
    void test_update_item_user_not_found() throws Exception {
        StoreItem item = initStoreItem();
        StoreItemUpdateRequest payload = DataUtils.testStoreItemUpdateRequest();
        payload.setItemCode(item.getCode());
        payload.setPostedByUser("123");
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(payload)))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), USER_REQUIRED);
    }

    @Test
    void test_update_item_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + UPDATE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new StoreItemUpdateRequest())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(), 3);
    }

    @Test
    void test_list_item_return_success() throws Exception {
        initStoreItem();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);
    }

    @Test
    void test_list_item_return_failed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), RECORD_NOT_FOUND);
    }

    @Test
    void test_list_item_by_category_code_return_success() throws Exception {
        StoreItem item = initStoreItem();
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_ITEM_BY_CATEGORY_CODE + "?code=" + item.getCategory().getCode())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);

    }

    @Test
    void test_list_item_by_category_code_return_failed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_ITEM_BY_CATEGORY_CODE + "?code=123")
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), ITEM_REQUIRED);

    }

    @Test
    void test_list_item_by_category_code_return_empty() throws Exception {
        StoreItemCategory storeItemCategory = storeItemCategoryRepo.save(DataUtils.testCreateStoreItemCategory());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST_ITEM_BY_CATEGORY_CODE + "?code=" + storeItemCategory.getCode())
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), Collections.emptyList());

    }

    @Test
    void test_list_item_category_return_success() throws Exception {
        initCategory();
        MvcResult mvcResult = mockMvc.perform(get(BASE + CATEGORY_LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<StoreItemCategoryResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(), 1);
    }

    @Test
    void test_list_item_category_return_empty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + CATEGORY_LIST)
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(), RECORD_NOT_FOUND);
        assertEquals(storeItemCategoryRepo.findAll(), Collections.emptyList());
    }
}
