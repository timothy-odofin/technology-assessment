package technology.assessment.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.UserRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.UserResponse;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.repository.UsersRepo;
import technology.assessment.app.util.CodeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static technology.assessment.app.util.AppCode.CREATED;
import static technology.assessment.app.util.AppCode.OKAY;
import static technology.assessment.app.util.MessageUtil.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
    private final UsersRepo usersRepo;

    @Override
    public ApiResponse<List<UserResponse>> list(int page, int size) {
        Page<Users> usersPage = usersRepo.findAll(PageRequest.of(page,size, Sort.by("dateCreated").descending()));
        if(usersPage.isEmpty())
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        return new ApiResponse<>(SUCCESS,OKAY, Mapper.convertList(usersPage.getContent(),UserResponse.class));
    }

    @Override
    public ApiResponse<String> addUser(UserRequest payload) {
        Users user =Users.builder()
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .userToken(CodeUtil.generateCode())
                .registeredDate(payload.getCreationDate()==null? LocalDate.now():payload.getCreationDate())
                .userCategory(payload.getUserCategory().name())
                .build();
        usersRepo.save(user);
        return new ApiResponse<>(SUCCESS,CREATED, USER_CREATED);
    }
}
