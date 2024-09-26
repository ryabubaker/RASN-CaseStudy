package com.example.rasnassesment.controller;

import com.example.rasnassesment.model.request.UserDetailsRequest;
import com.example.rasnassesment.model.response.UserResponse;
import com.example.rasnassesment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDetailsRequest userDetails) {
        UserResponse createdUser = userService.createUser(userDetails);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserByUserId(id);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserDetailsRequest userDetails) {
        UserResponse updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
    @PreAuthorize("hasAuthority('USER_MANAGE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}