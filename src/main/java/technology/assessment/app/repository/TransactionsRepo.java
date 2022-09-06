package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.Transactions;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {
}
