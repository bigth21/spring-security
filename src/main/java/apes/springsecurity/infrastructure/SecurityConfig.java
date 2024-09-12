package apes.springsecurity.infrastructure;

import apes.springsecurity.core.persistence.AccountRepository;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.stereotype.Component;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 30)
public class SecurityConfig {

    public static final String[] STATIC_RESOURCES = {"/css/**", "/images/**"};

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices, WebSecurity webSecurity) throws Exception {
        http
                .securityContext(securityContext -> securityContext
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()
                        )))
                .csrf(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/sign-out")
                        .logoutSuccessUrl("/"))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/sign-in").permitAll())
                .sessionManagement(session -> session
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1)))
                .rememberMe(remember -> remember
                        .rememberMeServices(rememberMeServices))
                .addFilterBefore(new TenantFilter(), AuthorizationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, STATIC_RESOURCES).permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{id}/**").access(
                                (authenticationManager, requestAuthorizationContext) -> {
                                    var id = Long.valueOf(requestAuthorizationContext.getVariables().get("id"));
                                    boolean granted = webSecurity.checkUserId(authenticationManager.get(), id);
                                    return new AuthorizationDecision(granted);
                                })
                        .requestMatchers(HttpMethod.GET, "/anonymous").permitAll()
                        .requestMatchers(HttpMethod.GET, "/privacy-policy").permitAll()
                        .requestMatchers(HttpMethod.GET, "/terms-of-service").permitAll()
                        .requestMatchers(HttpMethod.GET, "/errors/500").permitAll()
                        .requestMatchers("/sign-up").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/staff").hasRole("STAFF")
                        .requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        var providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    UserDetailsService userDetailsService(AccountRepository accountRepository) {
        return new DefaultUserDetailsService(accountRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    RememberMeServices rememberMeServices() {
        var rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setValiditySeconds(60 * 60 * 24);
        return rememberMeServices;
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("STAFF")
                .build();
    }

    // If using pre-post method security, following configuration must be added
//    @Bean
//    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
//        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        return expressionHandler;
//    }

    @Component
    static class WebSecurity {
        public boolean checkUserId(Authentication authentication, Long userId) {
            if (authentication.getName().equals("anonymousUser"))
                return false;
            DefaultUserDetails principal = (DefaultUserDetails) authentication.getPrincipal();
            return principal.getUserId().equals(userId);
        }
    }
}
