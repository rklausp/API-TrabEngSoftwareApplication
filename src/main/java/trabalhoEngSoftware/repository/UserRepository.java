package trabalhoEngSoftware.repository;


import trabalhoEngSoftware.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByNameContainingIgnoreCase(String search);

    Optional<Users> findByUsername(String username);
}
