package com.mika.mikabackend.service;

import com.mika.mikabackend.dto.ChangePasswordRequest;
import com.mika.mikabackend.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    List<User> getAllUsers();
}
