package com.mika.mikabackend.controller;

import com.mika.mikabackend.dto.ChangePasswordRequest;
import com.mika.mikabackend.dto.UpdateProfileRequest;
import com.mika.mikabackend.model.User;
import com.mika.mikabackend.service.UserService;
import com.mika.mikabackend.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/me")
    public User getCurrenUser(@RequestParam String email) {
        return service.getCurrenUser(email);
    }

    @PatchMapping("/updateUser/{id}")
    @Operation(summary = "Update user (updateUser)")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<User> updateUser(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateProfileRequest profile
    ) {
        return ResponseEntity.ok(
                service.updateUser(id, profile)
        );
    }

    @DeleteMapping("/deleteUser/{id}")
    @Operation(summary = "Delete user (deleteUser)")
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") String id
    ) {
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
