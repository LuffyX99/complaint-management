package com.college.complaints.repository;

import com.college.complaints.model.CollegeRequest;
import com.college.complaints.model.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollegeRequestRepository extends MongoRepository<CollegeRequest, String> {
    List<CollegeRequest> findByOwnerUserIdOrderByCreatedAtDesc(String ownerUserId);

    List<CollegeRequest> findAllByOrderByCreatedAtDesc();

    long countByStatus(RequestStatus status);
}
