package carrental.local.carrental.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.local.carrental.Entity.RentalOrder;
import carrental.local.carrental.Repository.RentalOrderRepository;

@Service
public class RentalOrderService {

    @Autowired
    private RentalOrderRepository rentalOrderRepository;

    public RentalOrder saveRentalOrder(RentalOrder rentalOrder) {
        rentalOrder.setStatus("false");
        return rentalOrderRepository.save(rentalOrder);
    }
}
