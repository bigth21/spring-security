package apes.springsecurity.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordEncoderTest {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void encode() {
        String encoded = passwordEncoder.encode("1234");
        System.out.println("encoded = " + encoded);
    }

}