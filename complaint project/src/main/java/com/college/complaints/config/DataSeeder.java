package com.college.complaints.config;

import com.college.complaints.model.Role;
import com.college.complaints.model.UserAccount;
import com.college.complaints.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserAccountRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminId;
    private final String adminPassword;
    private final String userId;
    private final String userPassword;

    public DataSeeder(
            UserAccountRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed.admin-id}") String adminId,
            @Value("${app.seed.admin-password}") String adminPassword,
            @Value("${app.seed.user-id}") String userId,
            @Value("${app.seed.user-password}") String userPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.userId = userId;
        this.userPassword = userPassword;
    }

    @Override
    public void run(String... args) {
        createDefaultUser(adminId, "College Administrator", "admin@campus.edu", "Administration", adminPassword, Role.ADMIN);
        createDefaultUser(userId, "Demo Student", "student@campus.edu", "Computer Science", userPassword, Role.USER);
    }

    private void createDefaultUser(String userId, String name, String email, String department, String password, Role role) {
        if (userRepository.existsByUserId(userId)) {
            return;
        }

        UserAccount user = new UserAccount();
        user.setUserId(userId);
        user.setFullName(name);
        user.setEmail(email);
        user.setDepartment(department);
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
