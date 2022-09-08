package technology.assessment.app.repository.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import technology.assessment.app.model.entity.Transactions;
import technology.assessment.app.repository.TransactionsRepo;

import javax.persistence.PrePersist;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionListener {
    private final TransactionsRepo transactionsRepo;

    @PrePersist
    public void onPrePersist(final Transactions toSave){

    }
}
