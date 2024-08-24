package apes.springsecurity.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "home/unauth";
    }

    @GetMapping("/auth")
    public String auth() {
        return "home/auth";
    }
}
