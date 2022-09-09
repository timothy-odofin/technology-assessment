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
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.service.UserService;
import technology.assessment.app.util.ParamName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;
import static technology.assessment.app.util.UserEndpoints.*;

@WebMvcTest(UserController.class)
@Slf4j
class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserService userService;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void test_add_user_return_success() throws Exception {
        when(userService.addUser(any())).thenReturn(DataUtils.createUserResponse());
        MvcResult mvcResult = mockMvc.perform(post(BASE + ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), CREATED);
        assertEquals(result.getMessage(), SUCCESS);

    }
    @Test
    void test_add_user_return_bad_request() throws Exception {
        Mockito.doThrow(new BadRequestException(String.join(",",DataUtils.userError())))
                .when(userService).list(anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), BAD_REQUEST);
        assertEquals(result.getMessage(), FAILED);
        assertEquals(result.getData(),String.join(",",DataUtils.userError()));

    }

    @Test
    void test_list_user_return_success() throws Exception {
        when(userService.list(anyInt(),anyInt())).thenReturn(DataUtils.testUsersResponse());
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
        Mockito.doThrow(new RecordNotFoundException(RECORD_NOT_FOUND))
                .when(userService).list(anyInt(),anyInt());
        MvcResult mvcResult = mockMvc.perform(get(BASE + LIST)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiResponse<String> result = mapFromJson(content, ApiResponse.class);
        assertEquals(result.getCode(), NOT_FOUND);
        assertEquals(result.getMessage(), FAILED);

    }
}
