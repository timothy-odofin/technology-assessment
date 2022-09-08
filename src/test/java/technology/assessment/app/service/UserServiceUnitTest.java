package technology.assessment.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import technology.assessment.app.model.DataUtils;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.repository.UsersRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static technology.assessment.app.util.AppCode.CREATED;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {
    @Mock
    private UsersRepo usersRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void test_list_user_return_success()  {
        when(usersRepo.save(any())).thenReturn(DataUtils.testUsers().get(0));
        ApiResponse<String> result = userService.addUser(DataUtils.testUser());
        assertThat(result.getCode()).isSameAs(CREATED);
        verify(usersRepo).save(DataUtils.testUser());
    }
}
