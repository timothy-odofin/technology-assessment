package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.StoreItemCategory;

import java.util.Optional;

public interface StoreItemCategoryRepo extends JpaRepository<StoreItemCategory,Long> {
    Optional<StoreItemCategory> findByCode(String code);
}
