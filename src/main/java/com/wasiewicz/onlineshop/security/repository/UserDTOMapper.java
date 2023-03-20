package com.wasiewicz.onlineshop.security.repository;

import com.wasiewicz.onlineshop.security.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDTOMapper implements Function<AppUser, UserDTO> {
    @Override
    public UserDTO apply(AppUser appUser) {
        return new UserDTO(
                appUser.getId(),
                appUser.getFirstname(),
                appUser.getLastname(),
                appUser.getEmail(),
                appUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }
}
