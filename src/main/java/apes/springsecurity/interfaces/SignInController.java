package apes.springsecurity.interfaces;

import apes.springsecurity.infrastructure.DefaultUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
    @GetMapping("/sign-in")
    String signIn(@AuthenticationPrincipal DefaultUserDetails user) {
        if (user != null) {
            return "redirect:/";
        }
        return "sign-in";
    }
}
