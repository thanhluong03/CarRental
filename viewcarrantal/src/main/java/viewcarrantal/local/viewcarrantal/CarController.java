package viewcarrantal.local.viewcarrantal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {
    @GetMapping("/add")
    public String addcar() {
        return "Car/caradd";
    }
    @GetMapping("/listcar")
    public String listcar() {
        return "Car/listcar";
    }

    @GetMapping("/updatecar")
    public String updatecar() {
        return "Car/carupdate";
    }
}
