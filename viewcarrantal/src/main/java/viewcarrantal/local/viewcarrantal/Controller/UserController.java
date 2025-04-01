package viewcarrantal.local.viewcarrantal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/listuser")
    public String listuser() {
        return "User/listuser";
    }
    @GetMapping("/updateuser")
    public String updateuser() {
        return "User/userupdate";
    }
}
