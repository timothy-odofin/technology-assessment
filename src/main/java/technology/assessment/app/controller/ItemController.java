package technology.assessment.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import technology.assessment.app.model.dto.request.StoreItemCategoryRequest;
import technology.assessment.app.model.dto.request.StoreItemRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.StoreItemCategoryResponse;
import technology.assessment.app.model.dto.response.StoreItemResponse;
import technology.assessment.app.service.ItemService;

import javax.validation.Valid;
import java.util.List;

import static technology.assessment.app.util.ItemEndPoints.*;
import static technology.assessment.app.util.ParamName.*;
import static technology.assessment.app.util.ParamName.SIZE_DEFAULT;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping(CATEGORY_ADD)
    ApiResponse<String> addItemCategory(@Valid @RequestBody StoreItemCategoryRequest payload){
        return itemService.addItemCategory(payload);
    }
    @PostMapping(ADD)
    ApiResponse<String> addItem(@Valid @RequestBody StoreItemRequest payload){

        return itemService.addItem(payload);
    }
    @GetMapping(LIST)
    ApiResponse<List<StoreItemResponse>> listItem(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                  @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size){
     return itemService.listItem(page,size);

    }
    @GetMapping(LIST_ITEM_BY_CATEGORY_CODE)
    ApiResponse<List<StoreItemResponse>> listItemByCategory(@RequestParam(value = CODE) String code){
        return itemService.listItemByCategory(code);

    }
    @GetMapping(CATEGORY_LIST)
    ApiResponse<List<StoreItemCategoryResponse>> listItemCategory(@RequestParam(value = PAGE, defaultValue = PAGE_DEFAULT) int page,
                                                                  @RequestParam(value = SIZE, defaultValue = SIZE_DEFAULT) int size){
       return itemService.listItemCategory(page, size);

    }
}
