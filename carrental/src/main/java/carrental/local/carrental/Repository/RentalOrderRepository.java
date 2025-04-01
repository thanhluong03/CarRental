package carrental.local.carrental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carrental.local.carrental.Entity.RentalOrder;

public interface RentalOrderRepository extends JpaRepository<RentalOrder, Long> {}
