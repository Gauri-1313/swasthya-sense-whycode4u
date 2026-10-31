package com.swasthya.sense.repository;

import com.swasthya.sense.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Patient entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find all patients who require clinical review.
     * Custom query derived method as specified in architecture requirements.
     */
    List<Patient> findByRequiresClinicalReviewTrue();

    /**
     * Optional custom JPQL query for prioritized alerts ordered by missed follow-ups.
     */
    @Query("SELECT p FROM Patient p WHERE p.requiresClinicalReview = true ORDER BY p.missedFollowUps DESC, p.lastCheckIn ASC")
    List<Patient> findHighPriorityAlerts();

    /**
     * Find patients filtered by risk category.
     */
    List<Patient> findByRiskCategoryIgnoreCase(String riskCategory);
}

