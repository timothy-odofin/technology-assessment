package technology.assessment.app.service;

import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.request.StoreItemUpdateRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.Users;

import java.util.List;

public interface ItemService {
     Users validateUser(String userToken);
    StoreItem findStoreItem(String code);
    ApiResponse<String> addItemCategory(StoreItemCategoryRequest payload);
    ApiResponse<String> addItem(StoreItemRequest payload);
    ApiResponse<String> updateItem(StoreItemUpdateRequest payload);
    ApiResponse<List<StoreItemResponse>> listItem(int page, int size);
    ApiResponse<List<StoreItemCategoryResponse>> listItemCategory(int page,int size);
    ApiResponse<List<StoreItemResponse>> listItemByCategory(String categoryCode);

}
