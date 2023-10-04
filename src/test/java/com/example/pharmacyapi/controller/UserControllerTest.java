package com.example.pharmacyapi.controller;

import com.example.pharmacyapi.model.request.LoginRequest;
import com.example.pharmacyapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    Long userId = 1L;
    @BeforeEach // This initializes Mockito annotations before each test method
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    

    @Test
    public void createUser() throws Exception {
        User USER_1 = new User(1L, "cigana", "pombagira@gmail.com", "quimbanda7", null);

        when(userRepository.save(USER_1)).thenReturn(USER_1);
        // Call the createUser method to create a new user
        User newUser = userService.createUser(USER_1);
        verify(userRepository,times(1)).save(USER_1);
        assertNotNull(newUser.getId());
        assertEquals("cigana",newUser.getUserName());
        assertEquals("pombagira@gmail.com",newUser.getEmailAddress());
    }


    @Test
    public void loginUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        Optional<String> jwtToken = Optional.of("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXJlc2gyQGdhLmNvbSIsImlhdCI6MTY5NTA1MjE5NSwiZXhwIjoxNjk1MTM4NTk1fQ.w9-8v-1uEd9IOhU-kX98vqKiqClghSIFPU1T0X7KQgo");

        when(userService.loginUser(Mockito.any(LoginRequest.class))).thenReturn(jwtToken);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/users/login/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value(jwtToken.get()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

}
