package com.wasiewicz.onlineshop.security.repository;

import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOMapperTest {

    private UserDTOMapper userDTOMapperUnderTest;

    @BeforeEach
    void setUp() {
        userDTOMapperUnderTest = new UserDTOMapper();
    }

    @Test
    void testApply() {
        // Setup
        final AppUser appUser = AppUser.builder()
                .id(0L)
                .firstname("firstname")
                .lastname("lastname")
                .email("email")
                .role(Role.USER)
                .build();
        final UserDTO expectedResult = new UserDTO(0L, "firstname", "lastname", "email", List.of("USER"));

        // Run the test
        final UserDTO result = userDTOMapperUnderTest.apply(appUser);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
