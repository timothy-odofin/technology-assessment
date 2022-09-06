package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.Users;

public interface UsersRepo extends JpaRepository<Users,Long> {
}
