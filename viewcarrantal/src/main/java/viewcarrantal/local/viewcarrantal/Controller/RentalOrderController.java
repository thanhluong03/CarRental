package viewcarrantal.local.viewcarrantal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalOrderController {
    @GetMapping("/rentalOrdercar")
    public String rentalOrdercar() {
        return "Home/rentalOrdercar";
    }
}
