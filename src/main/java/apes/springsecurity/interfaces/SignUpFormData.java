package apes.springsecurity.interfaces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpFormData {
    private String username;
    private String password;
    private String passwordRepeated;
}
