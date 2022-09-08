package technology.assessment.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.repository.UsersRepo;
import technology.assessment.app.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static technology.assessment.app.util.AppCode.CREATED;
import static technology.assessment.app.util.MessageUtil.SUCCESS;
import static technology.assessment.app.util.RestMapper.mapFromJson;
import static technology.assessment.app.util.RestMapper.mapToJson;
import static technology.assessment.app.util.UserEndpoints.*;

@WebMvcTest(UserController.class)
@Slf4j
class UserControllerTest {
//
//    @MockBean
//    private UserService userService;
@Mock
private UsersRepo usersRepo;
@InjectMocks
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_list_user_return_success() throws Exception {
        when(usersRepo.save(any())).thenReturn(DataUtils.testUsers().get(0));
//        MvcResult mvcResult = mockMvc.perform(post(BASE +ADD)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
//                .andExpect(status().isOk()).andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("*****************************************");
//        log.info(content);
//        ApiResponse result = mapFromJson(content, ApiResponse.class);
//        assertEquals(result.getCode(),CREATED);
//        assertEquals(result.getMessage(), SUCCESS);
        mockMvc.perform(post(BASE +ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(DataUtils.testUser())))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void addUser() {
    }
}
