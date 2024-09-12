package apes.springsecurity.infrastructure;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {
    public boolean checkUserId(Authentication authentication, RequestAuthorizationContext context) {
        if (authentication.getName().equals("anonymousUser"))
            return false;
        DefaultUserDetails principal = (DefaultUserDetails) authentication.getPrincipal();
        return principal.getUserId().equals(Long.valueOf(context.getVariables().get("userId")));
    }
}
