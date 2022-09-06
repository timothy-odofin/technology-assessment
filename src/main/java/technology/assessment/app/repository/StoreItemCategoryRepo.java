package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.StoreItemCategory;

public interface StoreItemCategoryRepo extends JpaRepository<StoreItemCategory,Long> {
}
