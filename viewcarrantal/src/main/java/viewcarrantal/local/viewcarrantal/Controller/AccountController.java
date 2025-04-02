package viewcarrantal.local.viewcarrantal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @GetMapping("/login")
    public String login() {
        return "Account/login";
    }
    @GetMapping("/register")
    public String register() {
        return "Account/register";
    }

}
