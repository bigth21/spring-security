package apes.springsecurity.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
    @GetMapping("/sign-in")
    String signIn() {
        return "sign-in";
    }
}
