// package carrental.local.carrental.Service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import carrental.local.carrental.Entity.Car;
// import carrental.local.carrental.Repository.CarRepository;

// @Service
// public class CarService {
//     @Autowired
//     private CarRepository carRepository;

//     // 📌 Lấy danh sách tất cả xe
//     public List<Car> getAllCars() {
//         return carRepository.findAll();
//     }

//     // 📌 Lấy xe theo ID
//     public Optional<Car> getCarById(Long id) {
//         return carRepository.findById(id);
//     }

//     // 📌 Thêm xe mới
//     public Car addCar(Car car) {
//         car.setStatus(false); // Gán trạng thái xe là false
//         return carRepository.save(car);
//     }
    


//     public Optional<Car> updateCar(Long id, Car updatedCar) {
//         return carRepository.findById(id).map(car -> {
//             car.setName(updatedCar.getName());
//             car.setBrand(updatedCar.getBrand());
//             car.setModel(updatedCar.getModel());
//             car.setYear(updatedCar.getYear());
//             car.setLicensePlate(updatedCar.getLicensePlate());
//             car.setRentalPrice(updatedCar.getRentalPrice());
//             return carRepository.save(car);
//         });
//     }

//     public boolean deleteCar(Long id) {
//         if (carRepository.existsById(id)) {
//             carRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }
// }

package carrental.local.carrental.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.local.carrental.Entity.Car;
import carrental.local.carrental.Repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }


    public Car addCar(Car car) {
        car.setStatus(false); 
        return carRepository.save(car);
    }
    

    public Car updateCar(Car car) {
        return carRepository.save(car);  
    }
    

    public boolean deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
