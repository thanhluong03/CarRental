package carrental.local.carrental.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import carrental.local.carrental.Entity.Car;
import carrental.local.carrental.Entity.RentalOrder;
import carrental.local.carrental.Entity.User;
import carrental.local.carrental.Service.CarService;
import carrental.local.carrental.Service.RentalOrderService;
import carrental.local.carrental.Service.UserService;

@RestController
@RequestMapping("/rental")
@CrossOrigin(origins = "http://localhost:8888")
public class RentalOrderController {
    private static final Logger logger = LoggerFactory.getLogger(RentalOrderController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private RentalOrderService rentalOrderService;

    @GetMapping("/orders")
    public ResponseEntity<List<RentalOrder>> getAllOrders() {
        try {
            List<RentalOrder> orders = rentalOrderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/order/{id}")
    public ResponseEntity<RentalOrder> getOrderById(@PathVariable Long id) {
        Optional<RentalOrder> order = rentalOrderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

@GetMapping("/create/{id}")
@CrossOrigin(origins = "http://localhost:8888")  
public ResponseEntity<Car> getCarByIdCreat(@PathVariable Long id) {
    Optional<Car> car = carService.getCarById(id);
    if (car.isPresent()) {
        return ResponseEntity.ok(car.get()); 
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  
    }
}

    
@PostMapping("/create")
@CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<?> createRentalOrder(
            @RequestParam("name") String fullName,
            @RequestParam("age") int age,
            @RequestParam("phone") String phoneNumber,
            @RequestParam("gender") String gender,
            @RequestParam("cccd") String cccd,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr,
            @RequestParam("totalPrice") double totalPrice,
            @RequestParam("depositAmount") double depositAmount,
            @RequestParam("drivingLicenseImage") MultipartFile drivingLicenseImage,
            @RequestParam("carId") Long carId) {

        try {
            Optional<Car> carOpt = carService.getCarById(carId);
            if (carOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy xe.");
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            byte[] drivingLicenseImageBytes = drivingLicenseImage.getBytes();

            Optional<User> existingUser = userService.findByCccd(cccd); 
            User user;

            if (existingUser.isPresent()) {
                user = existingUser.get();
            } else {
                user = new User(fullName, age, phoneNumber, gender, cccd, drivingLicenseImageBytes);
                user = userService.saveUser(user);
            }

            RentalOrder rentalOrder = new RentalOrder(user, carOpt.get(), startDate, endDate, totalPrice, depositAmount, "Chờ xác nhận");
            rentalOrderService.saveRentalOrder(rentalOrder);

            return ResponseEntity.ok("Đặt xe thành công!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tải ảnh: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<String> updateRentalOrder(@PathVariable Long id, @RequestBody RentalOrder updatedOrder) {
        try {

            Optional<RentalOrder> existingOrder = rentalOrderService.getOrderById(id);
            
            if (existingOrder.isPresent()) {
                RentalOrder order = existingOrder.get();
        
                order.setStartDate(updatedOrder.getStartDate());
                order.setEndDate(updatedOrder.getEndDate());
                order.setTotalPrice(updatedOrder.getTotalPrice());
                order.setStatus(updatedOrder.getStatus());
        

                if ("Đã duyệt".equals(updatedOrder.getStatus())) {
                    order.getCar().setStatus(true); 
                } else {
                    order.getCar().setStatus(false); 
                }
        
                rentalOrderService.saveRentalOrder(order);
                carService.addCar(order.getCar()); 
        
                return ResponseEntity.ok("Cập nhật hóa đơn thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóa đơn không tồn tại.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống.");
        }
    }
    
    @DeleteMapping("/delete/{id}")
@CrossOrigin(origins = "http://localhost:8888")
public ResponseEntity<String> deleteRentalOrder(@PathVariable Long id) {
    try {
        Optional<RentalOrder> orderOpt = rentalOrderService.getOrderById(id);

        if (orderOpt.isPresent()) {
            RentalOrder order = orderOpt.get();
            Car car = order.getCar();

            car.setStatus(false);
            carService.addCar(car);  

      
            rentalOrderService.deleteRentalOrder(id);

            return ResponseEntity.ok("Hóa đơn đã được xóa và trạng thái xe đã được cập nhật.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hóa đơn không tồn tại.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa hóa đơn.");
    }
}

}
