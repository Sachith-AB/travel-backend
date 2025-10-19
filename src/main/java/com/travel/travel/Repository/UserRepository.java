package com.travel.travel.Repository;

import com.travel.travel.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocId(String docId);
    Long countByIsDeleted(Boolean isDeleted);
    Long countByCreatedAtAfter(LocalDateTime date);
    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
