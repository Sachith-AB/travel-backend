package com.travel.travel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.travel.Models.Guid;

public interface GuidRepository extends JpaRepository<Guid, Long> {
    Optional<Guid> findByUserId(Long userId);
    
    // Find all available guides
    List<Guid> findByIsAvailableTrue();
    
    // Find guides by hourly rate range
    List<Guid> findByHoursRateBetween(Double minRate, Double maxRate);
    
    // Find guides by experience years minimum
    List<Guid> findByExperienceYearsGreaterThanEqual(Integer minExperience);
    
    // Custom query for complex search with multiple filters
    @Query("SELECT g FROM Guid g WHERE " +
           "(:availableOnly = false OR g.isAvailable = true) AND " +
           "(:minPrice IS NULL OR g.hoursRate >= :minPrice) AND " +
           "(:maxPrice IS NULL OR g.hoursRate <= :maxPrice) AND " +
           "(:languages IS NULL OR EXISTS (SELECT l FROM g.languagesSpoken l WHERE l IN :languages)) AND " +
           "(:specialties IS NULL OR EXISTS (SELECT s FROM g.specialization s WHERE s IN :specialties))")
    List<Guid> findGuidesWithFilters(
        @Param("languages") List<String> languages,
        @Param("specialties") List<String> specialties,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("availableOnly") Boolean availableOnly
    );
}
