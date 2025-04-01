
package carrental.local.carrental.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import carrental.local.carrental.Entity.Car;
import carrental.local.carrental.Service.CarService;
import carrental.local.carrental.Service.RentalOrderService;
import carrental.local.carrental.Service.UserService;

@RestController
@RequestMapping("/cars")
public class CarController {
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private RentalOrderService rentalOrderService;

    @ResponseBody

    @GetMapping("/listcar")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<List<Car>> getAllCars() {
        try {
            List<Car> cars = carService.getAllCars();
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách xe: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/homelistcar")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<List<Car>> getAllCarsByStatus() {
        try {
            List<Car> cars = carService.getAllCarsByStatus(false);
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/add")
@CrossOrigin(origins = "http://localhost:8888")
public ResponseEntity<?> addCar(@RequestParam("name") String name,
                                @RequestParam("brand") String brand,
                                @RequestParam("model") String model,
                                @RequestParam("year") int year,
                                @RequestParam("licensePlate") String licensePlate,
                                @RequestParam("rentalPrice") double rentalPrice,
                                @RequestParam(value = "image", required = false) MultipartFile image) {
    try {
        System.out.println("Dữ liệu nhận được: " + name + ", " + brand + ", " + model + ", " + year + ", " + licensePlate + ", " + rentalPrice);
        if (image != null) {
            System.out.println("Kích thước ảnh: " + image.getSize());
        } else {
            System.out.println("Không có ảnh được tải lên.");
        }

        Car car = new Car(name, brand, model, year, licensePlate, rentalPrice, false, image != null ? image.getBytes() : null);
        Car newCar = carService.addCar(car);

        return ResponseEntity.ok(newCar);
    } catch (IOException e) {
        logger.error("Lỗi khi xử lý ảnh: " + e.getMessage());
        return ResponseEntity.status(500).body("Lỗi khi tải ảnh");
    } catch (Exception e) {
        logger.error("Lỗi khi thêm xe: " + e.getMessage());
        return ResponseEntity.status(500).body("Lỗi hệ thống");
    }
}
    

    @GetMapping("/updatecar/{id}")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/updatecar/{id}")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, 
                                         @RequestParam("name") String name,
                                         @RequestParam("brand") String brand,
                                         @RequestParam("model") String model,
                                         @RequestParam("year") int year,
                                         @RequestParam("licensePlate") String licensePlate,
                                         @RequestParam("rentalPrice") double rentalPrice,
                                         @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Optional<Car> carOptional = carService.getCarById(id);
            if (carOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
    
            Car car = carOptional.get();
            car.setName(name);
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setLicensePlate(licensePlate);
            car.setRentalPrice(rentalPrice);
    
            
            if (image != null && !image.isEmpty()) {
                byte[] imageBytes = image.getBytes();
                car.setImage(imageBytes); 
            }
    
            Car updatedCar = carService.updateCar(car);  
            return ResponseEntity.ok(updatedCar);
        } catch (IOException e) {
            logger.error("Lỗi khi xử lý ảnh: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật xe: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
    

    @DeleteMapping("/deletecar/{id}")
    @CrossOrigin(origins = "http://localhost:8888")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        boolean deleted = carService.deleteCar(id);
        if (deleted) {
            logger.info("Xe với ID " + id + " đã được xóa thành công.");
            return ResponseEntity.noContent().build();
        } else {
            logger.error("Không tìm thấy xe với ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
    
}
