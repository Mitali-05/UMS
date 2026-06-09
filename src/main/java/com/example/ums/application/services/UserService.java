package com.example.ums.application.services;

import com.example.ums.application.dto.UserDetails;
import java.util.List;

public interface UserService {
    List<UserDetails> getAllUsers();
    UserDetails createUser(UserDetails userDetails);
    UserDetails getUserById(String id);
    UserDetails updateUserById(String id,UserDetails userDetails);
    void deleteUserById(String Id);
}
