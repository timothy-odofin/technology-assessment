package technology.assessment.app.service;

import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;

import java.util.List;

public interface ItemService {
    ApiResponse<String> addItemCategory(StoreItemCategoryRequest payload);
    ApiResponse<String> addItem(StoreItemRequest payload);
    ApiResponse<List<StoreItemResponse>> listItem(int page, int size);
    ApiResponse<List<StoreItemCategoryResponse>> listItemCategory(int page,int size);
    ApiResponse<List<StoreItemResponse>> listItemByCategory(String categoryCode);

}
