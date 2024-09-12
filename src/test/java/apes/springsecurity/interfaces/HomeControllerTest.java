package apes.springsecurity.interfaces;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @WithAnonymousUser
    @Test
    void home() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "STAFF")
    @Test
    void staff() throws Exception {
        mockMvc.perform(get("/staff"))
                .andExpect(status().isOk());
    }

    @WithUserDetails("user@abc.com")
    @Test
    void myPage() throws Exception {
        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk());
    }
}