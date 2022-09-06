package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.StoreItem;

public interface StoreItemRepo extends JpaRepository<StoreItem,Long> {
}
