package apes.springsecurity.interfaces;

import apes.springsecurity.core.persistence.AuthorityName;
import apes.springsecurity.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("user", new SignUpFormData());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("user") SignUpFormData signUpFormData) {
        if (!signUpFormData.getPassword().equals(signUpFormData.getPasswordRepeated()))
            throw new PasswordsNotEqualException();

        userService.createUser(signUpFormData.getUsername(), signUpFormData.getPassword(), List.of(AuthorityName.ROLE_USER));
        return "redirect:/sign-in";
    }
}
