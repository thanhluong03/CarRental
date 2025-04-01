package carrental.local.carrental.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.local.carrental.Entity.RentalOrder;
import carrental.local.carrental.Repository.RentalOrderRepository;

@Service
public class RentalOrderService {

    @Autowired
    private RentalOrderRepository rentalOrderRepository;

    public RentalOrder saveRentalOrder(RentalOrder rentalOrder) {
        return rentalOrderRepository.save(rentalOrder);
    }


    public List<RentalOrder> getAllOrders() {
        return rentalOrderRepository.findAll();
    }

    public Optional<RentalOrder> getOrderById(Long id) {
        return rentalOrderRepository.findById(id);
    }

    public void deleteRentalOrder(Long id) {
        Optional<RentalOrder> orderOpt = rentalOrderRepository.findById(id);

        if (orderOpt.isPresent()) {
            RentalOrder order = orderOpt.get();
            rentalOrderRepository.delete(order);  
        } else {
            throw new RuntimeException("Hóa đơn không tồn tại");  
        }
    }
}
