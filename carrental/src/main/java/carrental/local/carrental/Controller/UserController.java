package carrental.local.carrental.Controller;

import java.io.IOException;
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
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8888")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
        @Autowired
    private RentalOrderService rentalOrderService;
        @Autowired
    private CarService carService;

    @GetMapping("/listuser")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/updateuser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();
            user.setFullName(updatedUser.getFullName());
            user.setAge(updatedUser.getAge());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setGender(updatedUser.getGender());
            user.setCccd(updatedUser.getCccd());

            User savedUser = userService.updateUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật thông tin khách hàng: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa khách hàng: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

@PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestParam("fullName") String fullName,
                                           @RequestParam("age") int age,
                                           @RequestParam("phoneNumber") String phoneNumber,
                                           @RequestParam("gender") String gender,
                                           @RequestParam("cccd") String cccd,
                                           @RequestParam("driverLicenseImage") MultipartFile driverLicenseImage,
                                           @RequestParam("username") String username,
                                           @RequestParam("password") String password) {
        try {
            User user = new User();
            user.setFullName(fullName);
            user.setAge(age);
            user.setPhoneNumber(phoneNumber);
            user.setGender(gender);
            user.setCccd(cccd);
            user.setUsername(username);
            user.setPassword(password);  

            if (!driverLicenseImage.isEmpty()) {
    
                byte[] driverLicenseImageBytes = driverLicenseImage.getBytes();
                user.setDriverLicenseImage(driverLicenseImageBytes);  // Lưu ảnh dưới dạng byte[]

            }

            User newUser = userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IOException e) {
            logger.error("Lỗi khi xử lý ảnh: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Lỗi khi tạo người dùng: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam("username") String username, 
                                            @RequestParam("password") String password) {
        try {
            Optional<User> userOptional = userService.findByUsername(username);
            
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại.");
            }
            
            User user = userOptional.get();
            
            if (!user.getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không đúng.");
            }
            
            return ResponseEntity.ok("Đăng nhập thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống.");
        }
    }
    @GetMapping("/invoices")
    public ResponseEntity<List<RentalOrder>> getUserInvoices(@RequestParam("username") String username) {
        List<RentalOrder> orders = rentalOrderService.findOrdersByUserUsername(username);
        
        if (orders != null && !orders.isEmpty()) {
            return ResponseEntity.ok(orders);  
        } else {
            return ResponseEntity.status(404).body(null);  
        }
    }

    @GetMapping("/cars")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<List<Car>> getAllCarsByStatus() {
        try {
            List<Car> cars = carService.getAllCarsByStatus(false);
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/updateInvoice/{invoiceId}")
public ResponseEntity<RentalOrder> updateInvoice(@PathVariable Long invoiceId, @RequestBody RentalOrder updatedOrder) {
    Optional<RentalOrder> existingOrder = rentalOrderService.getOrderById(invoiceId);
    if (existingOrder.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    RentalOrder order = existingOrder.get();


    Optional<Car> selectedCar = carService.getCarById(updatedOrder.getCar().getId());
    if (selectedCar.isEmpty()) {
        return ResponseEntity.notFound().build();  
    }

    order.setCar(selectedCar.get());

    order.setStatus("Chờ xác nhận");

    rentalOrderService.saveCarrental(order);

    return ResponseEntity.ok(order);
}

    
}