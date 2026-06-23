package com.college.complaints.service;

import com.college.complaints.dto.CreateRequestForm;
import com.college.complaints.dto.StatusUpdateForm;
import com.college.complaints.model.CollegeRequest;
import com.college.complaints.model.RequestStatus;
import com.college.complaints.model.UserAccount;
import com.college.complaints.repository.CollegeRequestRepository;
import com.college.complaints.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    private final CollegeRequestRepository requestRepository;
    private final UserAccountRepository userRepository;

    public RequestService(CollegeRequestRepository requestRepository, UserAccountRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public CollegeRequest create(String userId, CreateRequestForm form) {
        UserAccount owner = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        CollegeRequest request = new CollegeRequest();
        request.setOwnerUserId(owner.getUserId());
        request.setOwnerName(owner.getFullName());
        request.setType(form.getType());
        request.setCategory(form.getCategory().trim());
        request.setTitle(form.getTitle().trim());
        request.setDescription(form.getDescription().trim());
        return requestRepository.save(request);
    }

    public List<CollegeRequest> findForUser(String userId) {
        return requestRepository.findByOwnerUserIdOrderByCreatedAtDesc(userId);
    }

    public List<CollegeRequest> findAll() {
        return requestRepository.findAllByOrderByCreatedAtDesc();
    }

    public void updateStatus(String id, StatusUpdateForm form) {
        CollegeRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found."));
        request.setStatus(form.getStatus());
        request.setAdminNote(form.getAdminNote() == null ? "" : form.getAdminNote().trim());
        request.setUpdatedAt(Instant.now());
        requestRepository.save(request);
    }

    public Map<RequestStatus, Long> statusCounts() {
        Map<RequestStatus, Long> counts = new EnumMap<>(RequestStatus.class);
        for (RequestStatus status : RequestStatus.values()) {
            counts.put(status, requestRepository.countByStatus(status));
        }
        return counts;
    }
}
