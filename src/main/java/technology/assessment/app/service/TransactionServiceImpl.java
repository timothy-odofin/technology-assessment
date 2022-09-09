package technology.assessment.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import technology.assessment.app.exception.BadRequestException;
import technology.assessment.app.exception.ItemOutOfStockException;
import technology.assessment.app.exception.RecordNotFoundException;
import technology.assessment.app.mapper.Mapper;
import technology.assessment.app.model.dto.request.TransactionRequest;
import technology.assessment.app.model.dto.response.ApiResponse;
import technology.assessment.app.model.dto.response.TransactionResponse;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.Transactions;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.repository.TransactionsRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static technology.assessment.app.util.AppCode.OKAY;
import static technology.assessment.app.util.AppUtil.formatMessage;
import static technology.assessment.app.util.MessageUtil.*;
import static technology.assessment.app.util.ParamName.USER_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final ItemService itemService;
    private final TransactionsRepo transactionsRepo;

    private Users validateUser(String userToken) {
        return itemService.validateUser(userToken);
    }

    private StoreItem validateItem(String itemCode) {
        return itemService.findStoreItem(itemCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ApiResponse<String> purchaseItem(TransactionRequest payload) {
        Users buyer = validateUser(payload.getBuyerToken());
        StoreItem item = validateItem(payload.getItemCode());
        if(payload.getQuantity()>item.getQuantity())
            throw new ItemOutOfStockException(OUT_OF_STOCK);
        Transactions transactions = Transactions.builder()
                .unitPrice(item.getPrice())
                .amount(item.getPrice() * payload.getQuantity())
                .item(item)
                .buyer(buyer)
                .quantityPurchased(payload.getQuantity())
                .build();
        transactionsRepo.save(transactions);
        item.setQuantity(item.getQuantity()- payload.getQuantity());
        itemService.sync(item);
        return new ApiResponse<>(SUCCESS, OKAY, TRANSACTION_PROCESSED_SUCCESSFULLY);
    }

    @Transactional(readOnly = true)
    protected ApiResponse<List<TransactionResponse>> fetchTransaction(String code, String category){
        List<Transactions> transactionsList = category.equals(ITEM)?transactionsRepo.listByItemCode(code):transactionsRepo.listByUserToken(code);
        if(transactionsList.isEmpty())
            throw new RecordNotFoundException(formatMessage(NO_TRANSACTION,category));
        return new ApiResponse<>(SUCCESS, OKAY,
                transactionsList.stream().sorted(Comparator.comparing(Transactions::getDateCreated).reversed())
                        .map(rs -> Mapper.convertObject(rs, TransactionResponse.class))
                        .collect(Collectors.toList()));
    }
    @Override
    public ApiResponse<List<TransactionResponse>> listTransactionByItemCode(String itemCode) {
        validateItem(itemCode);
       return fetchTransaction(itemCode,ITEM);
    }

    @Override
    public ApiResponse<List<TransactionResponse>> listTransactionByUser(String userCode) {
        validateUser(userCode);
        return fetchTransaction(userCode,USER_TOKEN);
    }

}
