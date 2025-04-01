package carrental.local.carrental.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import carrental.local.carrental.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCccd(String cccd);
}
