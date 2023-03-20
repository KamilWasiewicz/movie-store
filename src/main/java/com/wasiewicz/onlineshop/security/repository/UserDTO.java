package com.wasiewicz.onlineshop.security.repository;

import java.util.List;

public record UserDTO(
         Long id,
         String firstname,
         String lastname,
         String email,
         List<String> roles
) {
}
