package com.example.rasnassesment.service.impl;

import com.example.rasnassesment.entity.User;
import com.example.rasnassesment.exceptions.ResourceNotFoundException;
import com.example.rasnassesment.mapper.UserMapper;
import com.example.rasnassesment.model.request.UserDetailsRequest;
import com.example.rasnassesment.model.response.UserResponse;
import com.example.rasnassesment.repository.UserRepository;
import com.example.rasnassesment.security.UserPrincipal;
import com.example.rasnassesment.service.UserService;
import com.example.rasnassesment.utils.Utils;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Utils utils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.utils = utils;
    }

    @Override
    public UserResponse createUser(UserDetailsRequest userDetails) {
        User user = UserMapper.INSTANCE.userDetailsToUser(userDetails);
        String publicUserId = utils.generateUserId(30);
        user.setUserId(publicUserId);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserMapper.INSTANCE.userToUserResponse(user);
    }
    @Override
    public UserResponse getUserByUserId(String id) {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserDetailsRequest userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserMapper.INSTANCE.updateUserFromDto(userDetails, user);
        User updatedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserResponse(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
             throw new ResourceNotFoundException("User not found with email: " + email);
        }

        return new UserPrincipal(user);
    }



}
