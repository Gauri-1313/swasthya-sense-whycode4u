package com.swasthya.sense.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Patient Entity
 * 
 * Represents an anonymized patient profile tracked by community health workers.
 * Includes risk categorization, follow-up telemetry, and explainable rule-based
 * flags.
 * 
 * Note: Non-diagnostic prototype model for tracking and scheduling risk
 * mitigation.
 */
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Anonymized or pseudonymized identifier / patient display name
     * (e.g., "Patient #SS-8821 (Sunita D.)")
     */
    @Column(nullable = false)
    private String name;

    /**
     * Clinical tracking category: e.g. "Pregnancy", "Chronic", "Postnatal"
     */
    @Column(nullable = false)
    private String riskCategory;

    /**
     * Date of the most recent recorded check-in or field visit
     */
    @Column(nullable = false)
    private LocalDate lastCheckIn;

    /**
     * Cumulative count of missed or overdue follow-up appointments
     */
    @Column(nullable = false)
    private Integer missedFollowUps;

    /**
     * Rule-based trigger indicating whether a qualified clinician must review this
     * case
     */
    @Column(nullable = false)
    private Boolean requiresClinicalReview;

    /**
     * Plain-language explainability text clarifying the exact rule that triggered
     * the flag
     * (e.g., "Missed 2 consecutive routine check-ins; Gestational hypertension
     * monitor overdue.")
     */
    @Column(length = 500)
    private String riskFlagReason;

    /**
     * Current workflow state: "PENDING_REVIEW", "ROUTED_TO_CLINICIAN", "RESOLVED"
     */
    @Column(nullable = false)
    @Builder.Default
    private String status = "PENDING_REVIEW";

    /**
     * Privacy-preserving masked contact reference for community health worker
     */
    private String maskedContact;

    /**
     * Assigned community clinic / health center sector
     */
    private String sector;

    // Explicit Getters and Setters for robust interoperability
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public LocalDate getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(LocalDate lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public Integer getMissedFollowUps() {
        return missedFollowUps;
    }

    public void setMissedFollowUps(Integer missedFollowUps) {
        this.missedFollowUps = missedFollowUps;
    }

    public Boolean getRequiresClinicalReview() {
        return requiresClinicalReview;
    }

    public void setRequiresClinicalReview(Boolean requiresClinicalReview) {
        this.requiresClinicalReview = requiresClinicalReview;
    }

    public String getRiskFlagReason() {
        return riskFlagReason;
    }

    public void setRiskFlagReason(String riskFlagReason) {
        this.riskFlagReason = riskFlagReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaskedContact() {
        return maskedContact;
    }

    public void setMaskedContact(String maskedContact) {
        this.maskedContact = maskedContact;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
