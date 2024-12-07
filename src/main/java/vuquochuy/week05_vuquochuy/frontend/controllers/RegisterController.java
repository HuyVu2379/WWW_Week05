package vuquochuy.week05_vuquochuy.frontend.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "authorization/register"; // Tên của file `register.html`
    }
}