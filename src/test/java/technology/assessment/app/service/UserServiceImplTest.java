package technology.assessment.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.repository.UsersRepo;
import technology.assessment.app.util.BaseIT;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.model.DataUtils.testCreateUser;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;
import static technology.assessment.app.util.UserEndpoints.*;


class UserServiceImplTest extends BaseIT {
    @Autowired
    private UsersRepo usersRepo;
    @BeforeEach
    void setUp() {
        usersRepo.deleteAllInBatch();
    }
    @AfterEach
    void setUpDelete() {
        usersRepo.deleteAllInBatch();
    }
    @Test
    void test_add_user_return_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData(), DONE);

    }
    @Test
    void test_add_user_return_bad_request() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testBadUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<String>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData().size(),DataUtils.userError().size());

    }

    @Test
    void test_list_user_return_success() throws Exception {
usersRepo.save(testCreateUser());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<List<UserResponse>> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), OKAY);
        assertEquals(result.getMessage(), SUCCESS);
        assertEquals(result.getData().size(),1);
    }
    @Test
    void test_list_user_return_failed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),RECORD_NOT_FOUND);


    }
}
