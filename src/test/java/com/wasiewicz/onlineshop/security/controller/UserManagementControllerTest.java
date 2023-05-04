package com.wasiewicz.onlineshop.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasiewicz.onlineshop.security.model.AuthenticationRequest;
import com.wasiewicz.onlineshop.security.model.AuthenticationResponse;
import com.wasiewicz.onlineshop.security.model.RegisterRequest;
import com.wasiewicz.onlineshop.security.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserManagementControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private UserManagementController userManagementController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userManagementController).build();
    }

    @Test
    @DisplayName("Test register() method")
    public void testRegister() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("testuser");
        request.setPassword("testpassword");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("testtoken");

        when(authenticationService.register(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testtoken"));
    }

    @Test
    @DisplayName("Test authenticate() method")
    public void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("testuser@test.com");
        request.setPassword("testpassword");

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("testtoken");

        when(authenticationService.authenticate(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("testtoken"));
    }
}