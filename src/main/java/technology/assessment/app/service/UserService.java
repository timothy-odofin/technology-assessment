package technology.assessment.app.service;

import org.apache.catalina.realm.UserDatabaseRealm;
import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserResponse>> list(int page, int size);
    ApiResponse<String> addUser(UserRequest payload);


}
