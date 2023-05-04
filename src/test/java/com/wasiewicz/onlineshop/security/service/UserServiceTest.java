package com.wasiewicz.onlineshop.security.service;

import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.model.Role;
import com.wasiewicz.onlineshop.security.repository.UserDTO;
import com.wasiewicz.onlineshop.security.repository.UserDTOMapper;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserDTOMapper mockUserDTOMapper;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository, mockUserDTOMapper);
    }

    @Test
    void testGetUsers() {
        // Setup
        final List<UserDTO> expectedResult = List.of(
                new UserDTO(0L, "testUser", "testUserLastName", "test@example.com", List.of("ROLE_USER")));
        when(mockUserRepository.findAll()).thenReturn(List.of(AppUser.builder()
                .id(0L)
                .firstname("testUser")
                .lastname("testUserLastName")
                .email("test@example.com")
                .role(Role.USER)
                .build()));
        when(mockUserDTOMapper.apply(AppUser.builder()
                .id(0L)
                .firstname("testUser")
                .lastname("testUserLastName")
                .email("test@example.com")
                .role(Role.USER)
                .build())).thenReturn(expectedResult.get(0));

        // Run the test
        final List<UserDTO> result = userServiceUnderTest.getUsers();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetUsers_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserDTO> result = userServiceUnderTest.getUsers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetSingleUser() {
        // Setup
        final AppUser expectedResult = AppUser.builder()
                .id(0L)
                .firstname("testUser")
                .lastname("testUserLastName")
                .email("test@example.com")
                .role(Role.USER)
                .build();
        when(mockUserRepository.findById(0L)).thenReturn(Optional.of(expectedResult));

        // Run the test
        final AppUser result = userServiceUnderTest.getSingleUser(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSingleUser_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.getSingleUser(0L)).isInstanceOf(EntityNotFoundException.class);
    }
}
