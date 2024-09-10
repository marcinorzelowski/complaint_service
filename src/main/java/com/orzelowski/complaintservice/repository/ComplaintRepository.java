package com.orzelowski.complaintservice.repository;

import com.orzelowski.complaintservice.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByProductIdAndAuthorId(Long productId, Long authorId);

}
