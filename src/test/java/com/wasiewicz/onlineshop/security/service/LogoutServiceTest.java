package com.wasiewicz.onlineshop.security.service;

import com.wasiewicz.onlineshop.security.model.Token;
import com.wasiewicz.onlineshop.security.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

class LogoutServiceTest {

    private LogoutService logoutService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logoutService = new LogoutService(tokenRepository);
    }

    @Test
    void testLogout() {
        String authToken = "Bearer token";
        Token token = new Token();
        token.setToken(authToken.substring(7));

        when(request.getHeader("Authorization")).thenReturn(authToken);
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, times(1)).save(token);
        Assertions.assertTrue(token.isExpired());
        Assertions.assertTrue(token.isRevoked());
    }
}
