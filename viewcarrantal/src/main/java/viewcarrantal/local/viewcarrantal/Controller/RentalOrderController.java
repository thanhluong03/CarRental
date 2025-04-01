package viewcarrantal.local.viewcarrantal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalOrderController {
    @GetMapping("/rentalOrdercar")
    public String rentalOrdercar() {
        return "RentalCar/rentalOrdercar";
    }

    @GetMapping("/listrentalorder")
    public String listrentalorder() {
        return "RentalCar/listRentalOrder";
    }


    @GetMapping("/updaterentalorder")
    public String updaterentalorder() {
        return "RentalCar/rentalorderupdate";
    }
}
