package com.secure.books.services;

import java.util.List;
import java.util.Optional;

import com.secure.books.dtos.UserDTO;
import com.secure.books.models.User;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}
