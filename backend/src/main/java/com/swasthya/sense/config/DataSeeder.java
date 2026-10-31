package com.swasthya.sense.config;

import com.swasthya.sense.entity.Patient;
import com.swasthya.sense.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Data Seeder Component
 * 
 * Injects 10 synthetic, anonymized patient records into the database upon application startup.
 * Provides instant demonstration data for hackathon judges and health worker dashboards.
 * Demonstrates transparent, rule-based clinical review flags.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);
    private final PatientRepository patientRepository;

    public DataSeeder(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) {
        if (patientRepository.count() == 0) {
            logger.info("Initializing Swasthya Sense database with 10 synthetic anonymized patient records...");

            List<Patient> seedPatients = Arrays.asList(
                // 1. High-Risk Pregnancy - Multiple missed visits
                Patient.builder()
                    .name("Patient #SS-8821 (Sunita D.)")
                    .riskCategory("Pregnancy")
                    .lastCheckIn(LocalDate.now().minusDays(48))
                    .missedFollowUps(2)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 2 consecutive routine check-ins; Gestational hypertension monitor overdue.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 98XXX-XX821")
                    .sector("Sector 4 - Rural PHC")
                    .build(),

                // 2. Chronic - Type 2 Diabetes & Cardiac Risk
                Patient.builder()
                    .name("Patient #SS-1044 (Ramesh K.)")
                    .riskCategory("Chronic")
                    .lastCheckIn(LocalDate.now().minusDays(64))
                    .missedFollowUps(3)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 3 consecutive routine check-ins; Diabetic glycemic monitoring gap flagged.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 94XXX-XX044")
                    .sector("Sector 2 - Urban Health Post")
                    .build(),

                // 3. Postnatal - On Track (requiresClinicalReview = false)
                Patient.builder()
                    .name("Patient #SS-3390 (Meena P.)")
                    .riskCategory("Postnatal")
                    .lastCheckIn(LocalDate.now().minusDays(5))
                    .missedFollowUps(0)
                    .requiresClinicalReview(false)
                    .riskFlagReason(null)
                    .status("RESOLVED")
                    .maskedContact("+91 97XXX-XX390")
                    .sector("Sector 1 - Community Clinic")
                    .build(),

                // 4. Chronic - Hypertension
                Patient.builder()
                    .name("Patient #SS-7219 (Anil S.)")
                    .riskCategory("Chronic")
                    .lastCheckIn(LocalDate.now().minusDays(38))
                    .missedFollowUps(2)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 2 consecutive routine check-ins; hypertension medication refill lapsed.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 91XXX-XX219")
                    .sector("Sector 4 - Rural PHC")
                    .build(),

                // 5. High-Risk Pregnancy - Trimester 2
                Patient.builder()
                    .name("Patient #SS-5520 (Geeta V.)")
                    .riskCategory("Pregnancy")
                    .lastCheckIn(LocalDate.now().minusDays(32))
                    .missedFollowUps(2)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 2 consecutive routine check-ins; severe nutritional anemia history alert.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 96XXX-XX520")
                    .sector("Sector 3 - Sub-Center East")
                    .build(),

                // 6. Chronic - COPD - On Track
                Patient.builder()
                    .name("Patient #SS-6112 (Rajesh M.)")
                    .riskCategory("Chronic")
                    .lastCheckIn(LocalDate.now().minusDays(8))
                    .missedFollowUps(0)
                    .requiresClinicalReview(false)
                    .riskFlagReason(null)
                    .status("RESOLVED")
                    .maskedContact("+91 92XXX-XX112")
                    .sector("Sector 2 - Urban Health Post")
                    .build(),

                // 7. High-Risk Pregnancy - Anomaly Scan overdue
                Patient.builder()
                    .name("Patient #SS-4401 (Fatima B.)")
                    .riskCategory("Pregnancy")
                    .lastCheckIn(LocalDate.now().minusDays(42))
                    .missedFollowUps(2)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 2 consecutive routine antenatal check-ins; anomaly ultrasound scan window passed.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 99XXX-XX401")
                    .sector("Sector 1 - Community Clinic")
                    .build(),

                // 8. Pregnancy - First Trimester - On Track
                Patient.builder()
                    .name("Patient #SS-9031 (Kavita R.)")
                    .riskCategory("Pregnancy")
                    .lastCheckIn(LocalDate.now().minusDays(10))
                    .missedFollowUps(0)
                    .requiresClinicalReview(false)
                    .riskFlagReason(null)
                    .status("RESOLVED")
                    .maskedContact("+91 93XXX-XX031")
                    .sector("Sector 3 - Sub-Center East")
                    .build(),

                // 9. Chronic - Cardiovascular Tele-Care
                Patient.builder()
                    .name("Patient #SS-2810 (Suresh N.)")
                    .riskCategory("Chronic")
                    .lastCheckIn(LocalDate.now().minusDays(75))
                    .missedFollowUps(3)
                    .requiresClinicalReview(true)
                    .riskFlagReason("Missed 3 consecutive routine check-ins; telemetry blood pressure sync disconnected.")
                    .status("PENDING_REVIEW")
                    .maskedContact("+91 95XXX-XX810")
                    .sector("Sector 5 - District Hospital Annex")
                    .build(),

                // 10. Chronic - Controlled Asthma - On Track
                Patient.builder()
                    .name("Patient #SS-1944 (Pooja T.)")
                    .riskCategory("Chronic")
                    .lastCheckIn(LocalDate.now().minusDays(12))
                    .missedFollowUps(0)
                    .requiresClinicalReview(false)
                    .riskFlagReason(null)
                    .status("RESOLVED")
                    .maskedContact("+91 90XXX-XX944")
                    .sector("Sector 2 - Urban Health Post")
                    .build()
            );

            patientRepository.saveAll(seedPatients);
            logger.info("Successfully seeded {} synthetic patient records into Swasthya Sense database.", seedPatients.size());
        } else {
            logger.info("Database already contains {} patient records. Skipping seed data generation.", patientRepository.count());
        }
    }
}

