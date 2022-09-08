package technology.assessment.app.service;

import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    ApiResponse<String> purchaseItem(TransactionRequest payload);
    ApiResponse<List<TransactionResponse>> listTransactionByItemCode(String itemCode);
    ApiResponse<List<TransactionResponse>> listTransactionByUser(String userCode);
}
