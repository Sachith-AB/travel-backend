package com.travel.travel.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travel.Models.Guid;

public interface GuidRepository extends JpaRepository<Guid, Long> {
    Optional<Guid> findByUserId(Long userId);
}
