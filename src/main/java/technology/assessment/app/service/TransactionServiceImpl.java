package technology.assessment.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.repository.TransactionsRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{
    private final ItemService itemService;
    private final TransactionsRepo transactionsRepo;

    @Override
    public ApiResponse<String> purchaseItem(TransactionRequest payload) {
        return null;
    }

    @Override
    public ApiResponse<String> listTransactionByItemCode(String itemCode) {
        return null;
    }

    @Override
    public ApiResponse<String> listTransactionByUser(String userCode) {
        return null;
    }
}
