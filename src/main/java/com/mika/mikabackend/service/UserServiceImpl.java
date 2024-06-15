package com.mika.mikabackend.service;

import com.mika.mikabackend.dto.ChangePasswordRequest;
import com.mika.mikabackend.dto.UpdateProfileRequest;
import com.mika.mikabackend.model.User;
import com.mika.mikabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public User updateUser(String id, UpdateProfileRequest profile) {
        User user = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setFirstname(profile.getFirstName());
        user.setLastname(profile.getLastName());
        user.setPhone(profile.getPhone());
        user.setAddress(profile.getAddress());
//        user.setAvatar(profile.getAvatar());

        return repository.save(user);
//        redisTemplate.opsForHash().put(HASH_KEY, id, user);
//        redisTemplate.expire(HASH_KEY, CACHE_TTL, TimeUnit.SECONDS);
//        return userMapper.toUserDto(savedUser, "Update user profile successfully");
    }

    @Override
    public User getCurrenUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
