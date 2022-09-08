package technology.assessment.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.TransactionResponse;
import technology.assessment.app.service.TransactionService;
import javax.validation.Valid;
import java.util.List;
import static technology.assessment.app.util.ParamName.ITEM_CODE;
import static technology.assessment.app.util.ParamName.USER_TOKEN;
import static technology.assessment.app.util.TransactionEndpoints.*;

@RestController
@RequestMapping(BASE)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping
    ApiResponse<String> purchaseItem(@Valid @RequestBody TransactionRequest payload){
        return transactionService.purchaseItem(payload);
    }
    @GetMapping(LIST)
    ApiResponse<List<TransactionResponse>> listTransactionByItemCode(@RequestParam(ITEM_CODE) String itemCode){
        return transactionService.listTransactionByItemCode(itemCode);
    }
    @GetMapping(LIST_BY_BUYER)
    ApiResponse<List<TransactionResponse>> listTransactionByUser(@RequestParam(USER_TOKEN)String userToken){
        return transactionService.listTransactionByUser(userToken);

    }
}
