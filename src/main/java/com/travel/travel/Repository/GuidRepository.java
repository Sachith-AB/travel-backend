package com.travel.travel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.travel.travel.Models.Guid;
import com.travel.travel.RequestDto.GuidSearchDTO;

public interface GuidRepository extends JpaRepository<Guid, Long> {
    Optional<Guid> findByUserId(Long userId);

    @Query("""
            SELECT new com.travel.travel.RequestDto.GuidSearchDTO(
                g.id,
                u.firstName,
                g.bio,
                g.hoursRate * 8,
                g.experienceYears,
                g.isVerified,
                g.isAvailable,
                g.sltaLicenseId,
                g.sltaLicenseExpiry,
                g.nicNumber,
                g.createdAt
            )
            FROM Guid g
            JOIN g.user u
            WHERE g.isAvailable = true
                AND g.isVerified = true
                AND (g.sltaLicenseExpiry IS NULL OR g.sltaLicenseExpiry > CURRENT_TIMESTAMP)
            ORDER BY g.experienceYears DESC, g.createdAt DESC
            """)
    List<GuidSearchDTO> findAvailableVerifiedGuides();
}
