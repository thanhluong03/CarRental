package carrental.local.carrental.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import carrental.local.carrental.Entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(Boolean status);
}


