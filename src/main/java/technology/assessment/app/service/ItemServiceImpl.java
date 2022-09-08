package technology.assessment.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.StoreItemCategory;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.repository.StoreItemCategoryRepo;
import technology.assessment.app.repository.StoreItemRepo;
import technology.assessment.app.repository.UsersRepo;
import technology.assessment.app.util.CodeUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static technology.assessment.app.util.AppCode.CREATED;
import static technology.assessment.app.util.AppCode.OKAY;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.ParamName.SORTING_COL;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements  ItemService{
    private final StoreItemCategoryRepo storeItemCategoryRepo;
    private final StoreItemRepo storeItemRepo;
    private final UsersRepo usersRepo;

    public Users validateUser(String userToken){
        Optional<Users> usersOptional = usersRepo.findByUserToken(userToken);
        if(usersOptional.isEmpty())
            throw new RecordNotFoundException(USER_REQUIRED);
return usersOptional.get();
    }

    @Override
    public StoreItem findStoreItem(String code) {
        List<StoreItem> storeItems = storeItemRepo.checkExistence(code);
        if(storeItems.isEmpty())
            throw new RecordNotFoundException(ITEM_REQUIRED);
        return storeItems.get(0);
    }

    private StoreItemCategory validateCategory(String code){
        Optional<StoreItemCategory> storeItemCategory = storeItemCategoryRepo.findByCode(code);
        if(storeItemCategory.isEmpty())
            throw new RecordNotFoundException(ITEM_REQUIRED);
        return storeItemCategory.get();
    }

    @Override
    public ApiResponse<String> addItemCategory(StoreItemCategoryRequest payload) {
        Users user = validateUser(payload.getPostedByUser());
        StoreItemCategory storeItemCategory = Mapper.convertObject(payload,StoreItemCategory.class);
        storeItemCategory.setCreatedBy(user);
        storeItemCategory.setCode(CodeUtil.generateCode());
        storeItemCategoryRepo.save(storeItemCategory);
        return ApiResponse.<String>builder()
                .code(CREATED)
                .data(DONE)
                .message(SUCCESS)
                .build();
    }

    @Override
    public ApiResponse<String> addItem(StoreItemRequest payload) {
        Users user = validateUser(payload.getPostedByUser());
        StoreItemCategory category = validateCategory(payload.getCategoryCode());
        List<StoreItem> dbItem = storeItemRepo.checkExistence(payload.getItemCode());

        if(dbItem.isEmpty()){
            StoreItem newItem = Mapper.convertObject(payload,StoreItem.class);
            newItem.setCode(CodeUtil.generateCode());
            newItem.setCreatedBy(user);
            newItem.setCategory(category);
            storeItemRepo.save(newItem);
            return ApiResponse.<String>builder()
                    .code(CREATED)
                    .data(DONE)
                    .message(SUCCESS)
                    .build();
        }else{
            StoreItem oldItem = dbItem.get(0);
            oldItem.setQuantity(oldItem.getQuantity()+ payload.getQuantity());
            oldItem.setPrice(payload.getPrice());
            storeItemRepo.save(oldItem);
            return ApiResponse.<String>builder()
                    .code(OKAY)
                    .data(UPDATED)
                    .message(SUCCESS)
                    .build();
        }

    }

    @Override
    public ApiResponse<List<StoreItemResponse>> listItem(int page, int size) {
        Page<StoreItem> storeItemPage= storeItemRepo.findAll(PageRequest.of(page,size, Sort.by(SORTING_COL).descending()));
        if(storeItemPage.isEmpty())
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertList(storeItemPage.getContent(),StoreItemResponse.class));
    }

    @Override
    public ApiResponse<List<StoreItemCategoryResponse>> listItemCategory(int page, int size) {
        Page<StoreItemCategory> storeItemCategoryPage= storeItemCategoryRepo.findAll(PageRequest.of(page,size, Sort.by(SORTING_COL).descending()));
        if(storeItemCategoryPage.isEmpty())
            throw new RecordNotFoundException(RECORD_NOT_FOUND);
        return new ApiResponse<>(SUCCESS,OKAY,Mapper.convertList(storeItemCategoryPage.getContent(),StoreItemCategoryResponse.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<StoreItemResponse>> listItemByCategory(String categoryCode) {
       validateCategory(categoryCode);
       List<StoreItemResponse>  responseList= storeItemRepo.listByCategoryCode(categoryCode).filter(rs->rs!=null && rs.getId()!=null)
               .sorted(Comparator.comparing(StoreItem::getItemName))
               .map(rs->Mapper.convertObject(rs,StoreItemResponse.class)).collect(Collectors.toList());

        return new ApiResponse<>(SUCCESS,OKAY,responseList);

    }
}