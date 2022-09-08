package technology.assessment.app.repository.listener;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import technology.assessment.app.model.entity.StoreItem;
import technology.assessment.app.model.entity.Transactions;
import technology.assessment.app.model.entity.Users;
import technology.assessment.app.model.enums.AccountType;
import technology.assessment.app.repository.TransactionsRepo;
import technology.assessment.app.util.AppUtil;
import technology.assessment.app.util.CodeUtil;

import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Component
@Slf4j
public class TransactionListener {
    @PrePersist
    public void onPrePersist(final Transactions toSave) {
        updateDiscount(toSave);
        AccountType accountType = AccountType.valueOf(toSave.getDiscountType());
        toSave.setDiscount(toSave.getDiscount()>=0? toSave.getDiscount() : accountType.discountRate * toSave.getAmount());
        toSave.setNetAmount(toSave.getAmount() - toSave.getDiscount());
        toSave.setTranRef(CodeUtil.generateCode());
        StoreItem item = toSave.getItem();
        log.info("New Purchase {} By {}  at {} width description {}",
                item.getItemName(), toSave.getBuyer().getFullName(), LocalDateTime.now(),item.getDescription());
    }

    private void updateDiscount(Transactions toSave) {
        StoreItem item = toSave.getItem();
        if (item.bonusExclussion().contains(toSave.getItem().getCategory().getCategoryName().toLowerCase(Locale.ROOT))) {
            toSave.setDiscountType(AccountType.OTHERS.name());
            toSave.setDiscount(0.0);
        } else {
            Users buyer = toSave.getBuyer();
            Double amount = toSave.getAmount();
            AccountType otherAccountType =AccountType.OTHERS;
            if (buyer.getUserCategory().equalsIgnoreCase(AccountType.AFFILIATE.name())) {
                toSave.setDiscountType(AccountType.AFFILIATE.name());
                toSave.setDescription("Purchased item with 10% discount");
            }
            else if (buyer.getUserCategory().equalsIgnoreCase(AccountType.EMPLOYEE.name())) {
                toSave.setDiscountType(AccountType.EMPLOYEE.name());
                toSave.setDescription("Purchased item with 30% discount");
            }
            else if(buyer.getUserCategory().equalsIgnoreCase(AccountType.CUSTOMER.name()) &&
                    AppUtil.getYearDifference(buyer.getRegisteredDate(), LocalDate.now())>2) {
                toSave.setDiscountType(AccountType.CUSTOMER.name());
                toSave.setDescription("Purchased item with 15% discount(Customer over 2years)");
            }
            else if(amount>=otherAccountType.discountRate) {
                Double discountRate=amount/otherAccountType.discountRate;
                toSave.setDiscount((double) (discountRate.longValue() * 5));
                toSave.setDiscountType(otherAccountType.name());
                toSave.setDescription("Purchased item with bill >=100");
            }else{
                toSave.setDiscountType(AccountType.OTHERS.name());
                toSave.setDiscount(0.0);
                toSave.setDescription("Purchased item with no discount");
            }


        }
    }

}
