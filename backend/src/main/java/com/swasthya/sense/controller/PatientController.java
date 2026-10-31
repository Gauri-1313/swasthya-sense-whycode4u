package com.swasthya.sense.controller;

import com.swasthya.sense.entity.Patient;
import com.swasthya.sense.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Swasthya Sense Patient Management & Early-Warning Surveillance
 */
@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:3000"})
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Core Hackathon Endpoint:
     * GET /api/patients/alerts
     * 
     * Returns all high-risk patients requiring clinical review with explainable rule flag reasons.
     */
    @GetMapping("/alerts")
    public ResponseEntity<List<Patient>> getHighRiskAlerts() {
        List<Patient> highRiskPatients = patientRepository.findByRequiresClinicalReviewTrue();
        return ResponseEntity.ok(highRiskPatients);
    }

    /**
     * GET /api/patients
     * 
     * Returns full directory of monitored patients.
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientRepository.findAll());
    }

    /**
     * GET /api/patients/{id}
     * 
     * Retrieves specific patient details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/patients/{id}/route-to-clinician
     * 
     * Escalates a flagged patient to a qualified medical clinician.
     * Updates workflow status to "ROUTED_TO_CLINICIAN".
     */
    @PostMapping("/{id}/route-to-clinician")
    public ResponseEntity<Patient> routeToClinician(@PathVariable Long id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Patient patient = optionalPatient.get();
        patient.setStatus("ROUTED_TO_CLINICIAN");
        Patient updated = patientRepository.save(patient);
        return ResponseEntity.ok(updated);
    }

    /**
     * GET /api/patients/stats
     * 
     * Returns aggregated monitoring metrics for the Health Worker dashboard.
     */
    @GetMapping("/stats")
    public ResponseEntity<PatientStats> getStats() {
        List<Patient> all = patientRepository.findAll();
        long total = all.size();
        long flagged = all.stream().filter(p -> Boolean.TRUE.equals(p.getRequiresClinicalReview())).count();
        long pregnancyAlerts = all.stream()
                .filter(p -> Boolean.TRUE.equals(p.getRequiresClinicalReview()) && "Pregnancy".equalsIgnoreCase(p.getRiskCategory()))
                .count();
        long chronicAlerts = all.stream()
                .filter(p -> Boolean.TRUE.equals(p.getRequiresClinicalReview()) && "Chronic".equalsIgnoreCase(p.getRiskCategory()))
                .count();
        long routedCount = all.stream()
                .filter(p -> "ROUTED_TO_CLINICIAN".equalsIgnoreCase(p.getStatus()))
                .count();

        return ResponseEntity.ok(new PatientStats(total, flagged, pregnancyAlerts, chronicAlerts, routedCount));
    }

    public record PatientStats(long totalMonitored, long totalFlagged, long pregnancyAlerts, long chronicAlerts, long escalatedCount) {}
}

