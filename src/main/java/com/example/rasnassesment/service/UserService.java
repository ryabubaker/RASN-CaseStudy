package com.example.rasnassesment.service;

import com.example.rasnassesment.model.request.UserDetailsRequest;
import com.example.rasnassesment.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponse createUser(UserDetailsRequest userDetails);
    UserResponse getUser(String email);
    UserResponse getUserByUserId(String userId);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserDetailsRequest userDetails);
    void deleteUser(Long id);
}

