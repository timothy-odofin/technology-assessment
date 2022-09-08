package technology.assessment.app.repository.listener;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import technology.assessment.app.model.entity.Transactions;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.repository.TransactionsRepo;
import technology.assessment.app.util.CodeUtil;

import javax.persistence.PrePersist;

@Component
@Slf4j
public class TransactionListener {
    @PrePersist
    public void onPrePersist(final Transactions toSave) {
        AccountType accountType = AccountType.valueOf(toSave.getDiscountType());
        toSave.setDiscount(accountType.discountRate* toSave.getAmount());
        toSave.setNetAmount(toSave.getAmount()- toSave.getDiscount());
        toSave.setTranRef(CodeUtil.generateCode());

    }

}
