package carrental.local.carrental.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import carrental.local.carrental.Entity.RentalOrder;

public interface RentalOrderRepository extends JpaRepository<RentalOrder, Long> {
    List<RentalOrder> findByUserUsername(String username);
}
