package technology.assessment.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import technology.assessment.app.model.entity.Users;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByUserToken(String token);
}
