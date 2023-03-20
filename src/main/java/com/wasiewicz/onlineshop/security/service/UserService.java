package com.wasiewicz.onlineshop.security.service;

import com.wasiewicz.onlineshop.security.repository.UserDTO;
import com.wasiewicz.onlineshop.security.repository.UserDTOMapper;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());

    }
}
