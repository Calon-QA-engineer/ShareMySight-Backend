package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Object> {
    Optional<User> findByEmail(String username);
}
