package io.bootify.order.repos;

import io.bootify.order.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

}
