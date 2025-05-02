package practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practicum.model.User;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {



}
