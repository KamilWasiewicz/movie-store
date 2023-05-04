package com.wasiewicz.onlineshop.security.repository;

import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;
    @Test
    void testFindByEmail() {
        // given
        String email = "johndoe@example.com";
        AppUser testUser = AppUser.builder()
                .firstname("John")
                .lastname("Doe")
                .email(email)
                .password("password")
                .role(Role.USER)
                .build();
        underTest.save(testUser);
        // when
        Optional<AppUser> actualFindByEmailResult = underTest.findByEmail(email);

        // then
        assertThat(actualFindByEmailResult.isPresent()).isTrue();
        assertThat(actualFindByEmailResult.get().getEmail()).isEqualTo(email);

    }
}

