package com.wasiewicz.onlineshop.security.service;

import com.wasiewicz.onlineshop.security.model.*;
import com.wasiewicz.onlineshop.security.repository.TokenRepository;
import com.wasiewicz.onlineshop.security.repository.UserDTOMapper;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import com.wasiewicz.onlineshop.security.validator.ObjectValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private ObjectValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, jwtService,
                authenticationManager, tokenRepository, validator);
    }

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("johndoe@example.com");
        request.setPassword("password");

        AppUser appUser = new AppUser();
        appUser.setFirstname(request.getFirstname());
        appUser.setLastname(request.getLastname());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRole(Role.USER);

        String jwtToken = "jwt_token";

        when(userRepository.save(appUser)).thenReturn(appUser);
        when(jwtService.generateToken(appUser)).thenReturn(jwtToken);

        authenticationService.register(request);

        verify(validator, times(1)).validate(request);
        verify(userRepository, times(1)).save(appUser);
        verify(jwtService, times(1)).generateToken(appUser);

        Token token = Token.builder()
                .appUser(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void testAuthenticate() {
        // given
        String email = "test@example.com";
        String password = "testpassword";
        String encodedPassword = "encodedpassword";
        String authToken = "Bearer token";
        AppUser user = AppUser.builder()
                .email(email)
                .password(encodedPassword)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Token token = new Token(null, authToken, TokenType.BEARER, false, false, user);

        // when
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password))).thenReturn(new UsernamePasswordAuthenticationToken(email, password));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(authToken);
        when(tokenRepository.save(any(Token.class))).thenReturn(token);
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder().token(authToken).build();
        AuthenticationResponse actualResponse = authenticationService.authenticate(new AuthenticationRequest(email, encodedPassword));

        // then
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).findAllValidTokenByUser(user.getId());
        assertEquals(expectedResponse, actualResponse);
    }
}