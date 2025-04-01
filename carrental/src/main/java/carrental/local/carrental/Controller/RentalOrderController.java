package carrental.local.carrental.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class RentalOrderController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private RentalOrderService rentalOrderService;
    
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
    
}