package com.college.complaints.service;

import com.college.complaints.dto.CreateUserForm;
import com.college.complaints.model.UserAccount;
import com.college.complaints.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserAccountRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount createUser(CreateUserForm form) {
        if (userRepository.existsByUserId(form.getUserId())) {
            throw new IllegalArgumentException("This user ID already exists.");
        }

        UserAccount user = new UserAccount();
        user.setUserId(form.getUserId().trim());
        user.setFullName(form.getFullName().trim());
        user.setEmail(form.getEmail().trim());
        user.setDepartment(form.getDepartment().trim());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole(form.getRole());
        return userRepository.save(user);
    }

    public List<UserAccount> findAll() {
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

    public void toggleEnabled(String id) {
        UserAccount user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }
}
