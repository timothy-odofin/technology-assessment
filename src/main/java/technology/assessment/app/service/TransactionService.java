package technology.assessment.app.service;

import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;

public interface TransactionService {
    ApiResponse<String> purchaseItem(TransactionRequest payload);
    ApiResponse<String> listTransactionByItemCode(String itemCode);
    ApiResponse<String> listTransactionByUser(String userCode);
}
