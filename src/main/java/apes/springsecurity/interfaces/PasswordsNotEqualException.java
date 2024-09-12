package apes.springsecurity.interfaces;

public class PasswordsNotEqualException extends RuntimeException {
    public PasswordsNotEqualException() {
        super("Passwords not equal");
    }
}
