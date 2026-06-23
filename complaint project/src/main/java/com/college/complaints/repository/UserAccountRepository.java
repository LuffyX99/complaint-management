package com.college.complaints.repository;

import com.college.complaints.model.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    Optional<UserAccount> findByUserId(String userId);

    boolean existsByUserId(String userId);

    List<UserAccount> findAllByOrderByCreatedAtDesc();
}
