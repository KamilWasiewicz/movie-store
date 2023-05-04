package com.wasiewicz.onlineshop.security.service;

import com.wasiewicz.onlineshop.filmshop.model.Film;
import com.wasiewicz.onlineshop.security.model.AppUser;
import com.wasiewicz.onlineshop.security.repository.UserDTO;
import com.wasiewicz.onlineshop.security.repository.UserDTOMapper;
import com.wasiewicz.onlineshop.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public AppUser getSingleUser(long id) throws EntityNotFoundException {
        Optional<AppUser> singleUser = userRepository.findById(id);
        if (singleUser.isEmpty())
            throw new EntityNotFoundException("User id is invalid " + id);
        return singleUser.get();
    }
}
