package apes.springsecurity.core;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String username) {
        super(String.format("Username already exist with username '%s'", username));
    }
}
