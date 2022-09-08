package technology.assessment.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static technology.assessment.app.util.ParamName.*;
import static technology.assessment.app.util.UserEndpoints.*;
@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping(LIST)
    ApiResponse<List<UserResponse>> list(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                         @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size) {
        return userService.list(page, size);
    }
    @PostMapping(ADD)
    ApiResponse<String> addUser(@Valid @RequestBody UserRequest payload) {
        return userService.addUser(payload);
    }
}
