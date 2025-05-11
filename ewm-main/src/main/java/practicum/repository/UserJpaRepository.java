package practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practicum.model.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {



}
