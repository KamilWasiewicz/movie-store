package com.wasiewicz.onlineshop.security.repository;

import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.model.Token;
import com.wasiewicz.onlineshop.security.model.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // create some test data
        AppUser user1 = new AppUser();
        user1.setId(1L);
        user1.setFirstname("testuser1");
        user1.setPassword("password1");

        AppUser user2 = new AppUser();
        user2.setId(2L);
        user2.setFirstname("testuser2");
        user2.setPassword("password2");
        userRepository.save(user1);
        userRepository.save(user2);
        Token token1 = new Token();
        token1.setId(1);
        token1.setToken("token1");
        token1.setTokenType(TokenType.BEARER);
        token1.setRevoked(false);
        token1.setExpired(false);
        token1.setAppUser(user1);

        Token token2 = new Token();
        token2.setId(2);
        token2.setToken("token2");
        token2.setTokenType(TokenType.BEARER);
        token2.setRevoked(true);
        token2.setExpired(false);
        token2.setAppUser(user1);

        Token token3 = new Token();
        token3.setId(3);
        token3.setToken("token3");
        token3.setTokenType(TokenType.BEARER);
        token3.setRevoked(false);
        token3.setExpired(true);
        token3.setAppUser(user2);

        tokenRepository.save(token1);
        tokenRepository.save(token2);
        tokenRepository.save(token3);
    }

    @Test
    public void testFindAllValidTokenByUser() {
        List<Token> tokens = tokenRepository.findAllValidTokenByUser(2L);
        Assertions.assertEquals(1, tokens.size());
        Assertions.assertEquals("token3", tokens.get(0).getToken());
    }

    @Test
    public void testFindByToken() {
        Optional<Token> optionalToken = tokenRepository.findByToken("token2");
        Assertions.assertTrue(optionalToken.isPresent());
        Token token = optionalToken.get();
        Assertions.assertEquals(2, token.getId());
        Assertions.assertEquals(TokenType.BEARER, token.getTokenType());
        Assertions.assertTrue(token.isRevoked());
        Assertions.assertFalse(token.isExpired());
        Assertions.assertEquals("testuser1", token.getAppUser().getFirstname());
    }

}
