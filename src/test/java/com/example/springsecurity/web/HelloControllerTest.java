package com.example.springsecurity.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class HelloControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SecurityProperties securityProperties;

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/hello")
                        .with(httpBasic(securityProperties.getUser().getName(), securityProperties.getUser().getPassword())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello"));
    }
}