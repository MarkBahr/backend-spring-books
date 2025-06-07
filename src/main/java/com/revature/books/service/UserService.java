package com.revature.books.service;

import com.revature.books.dtos.UserDTO;
import com.revature.books.model.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);
}
