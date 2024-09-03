package apes.springsecurity.interfaces;

import apes.springsecurity.infrastructure.DefaultUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

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
        log.info("userDetails.getUsername(): {}", userDetails.getUsername());
        return "home/user";
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
