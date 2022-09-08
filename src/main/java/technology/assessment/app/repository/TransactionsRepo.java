package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.assessment.app.model.entity.Transactions;

import java.util.List;
import java.util.stream.Stream;

import static technology.assessment.app.util.ParamName.*;

public interface TransactionsRepo extends JpaRepository<Transactions,Long> {
    @Query("select tr from Transactions  tr where tr.item.code=:itemCode")
    List<Transactions> listByItemCode(@Param(ITEM_CODE) String itemCode);
    @Query("select tr from Transactions  tr where tr.buyer.userToken=:userToken")
    List<Transactions> listByUserToken(@Param(USER_TOKEN) String userToken);
}
