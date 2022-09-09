package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import technology.assessment.app.model.entity.StoreItem;

import java.util.List;
import java.util.stream.Stream;

public interface StoreItemRepo extends JpaRepository<StoreItem,Long> {
    @Query("select st from StoreItem  st where st.code=:code")
    List<StoreItem> checkExistence(@Param("code") String code);


    @Query("select st from StoreItem  st where st.category.code=:code")
    Stream<StoreItem> listByCategoryCode(@Param("code") String code);
}
