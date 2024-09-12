package apes.springsecurity.interfaces;

import apes.springsecurity.core.persistence.User;
import apes.springsecurity.core.persistence.UserRepository;
import apes.springsecurity.infrastructure.DefaultUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(@CurrentSecurityContext SecurityContext securityContext) {
        log.info("Authentication class: {}", securityContext.getAuthentication().getClass().getName());
        log.info("Authentication name: {}", securityContext.getAuthentication().getName());
        log.info("Principal class: {}", securityContext.getAuthentication().getPrincipal().getClass().getName());
        log.info("Principal: {}", securityContext.getAuthentication().getPrincipal());
        return "index";
    }

    @GetMapping("/anonymous")
    public String anonymous() {
        return "home/anonymous";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal DefaultUserDetails userDetails) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("name = " + name);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal = " + principal);
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        System.out.println("credentials = " + credentials);
        log.info("userDetails.getUsername(): {}", userDetails.getUsername());
        return "home/user";
    }

    @GetMapping("/users/{id}")
    public String myPage(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow();
        model.addAttribute("user", user);
        return "my-page";
    }

    @GetMapping("/staff")
    public String staff() {
        return "home/staff";
    }

    @GetMapping("/admin")
    public String admin() {
        return "home/admin";
    }

    @GetMapping("/errors/500")
    public String error() {
        throw new RuntimeException();
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "home/privacy-policy";
    }

    @GetMapping("/terms-of-service")
    public String termsOfService() {
        return "home/terms-of-service";
    }
}
