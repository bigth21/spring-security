package apes.springsecurity.interfaces;

import apes.springsecurity.infrastructure.MyUserDetails;
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

    @GetMapping("/unauth")
    public String unauth() {
        return "home/unauth";
    }

    @GetMapping("/auth")
    public String auth(@AuthenticationPrincipal MyUserDetails userDetails) {
        log.info("userDetails.getUsername(): {}", userDetails.getUsername());
        return "home/auth";
    }
}
