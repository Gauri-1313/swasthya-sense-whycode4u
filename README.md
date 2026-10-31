# Swasthya Sense (स्वास्थ्य सेंस)
### Privacy-Conscious Follow-Up & Early-Warning Surveillance Platform for Community Healthcare Workers

> **Hackathon Prototype Notice:** This application is an early-warning tracking and scheduling assistant. It does **not** perform automated medical diagnosis. All risk flags are deterministic, explainable, and rule-based to assist front-line health workers (ASHA/ANM) in identifying missed follow-ups and routing high-risk patients to qualified clinicians.

---

## 🏗️ Architecture & Monorepo Structure

```
swasthya-sense-whycode4u/
├── backend/
│   ├── pom.xml                                   # Maven build configuration (Java 17, Spring Boot 3.2.5)
│   └── src/
│       └── main/
│           ├── java/com/swasthya/sense/
│           │   ├── SwasthyaSenseApplication.java # Spring Boot entry point
│           │   ├── entity/
│           │   │   └── Patient.java              # JPA entity (Risk category, follow-ups, rule flag reason)
│           │   ├── repository/
│           │   │   └── PatientRepository.java    # Data JPA Repository with custom alert queries
│           │   ├── controller/
│           │   │   └── PatientController.java    # REST API (/api/patients/alerts, @CrossOrigin :5173)
│           │   └── config/
│           │       └── DataSeeder.java           # CommandLineRunner seeding 10 synthetic patient rows
│           └── resources/
│               └── application.properties        # MySQL connection & Hibernate configuration
│
├── frontend/
│   ├── package.json                              # Vite, React, Axios, Tailwind CSS, Lucide Icons
│   ├── vite.config.js                            # Vite dev server configuration (Port 5173)
│   ├── tailwind.config.js                        # Tailwind CSS styling tokens
│   ├── postcss.config.js                         # PostCSS configuration
│   ├── index.html                                # HTML shell with healthcare fonts & icons
│   └── src/
│       ├── main.jsx                              # React root entry point
│       ├── index.css                             # Tailwind directives & styles
│       └── App.jsx                               # Health Worker Dashboard & explainable alert roster
│
└── README.md
```

---

## ⚡ Quick Start Guide

### 1. Backend Setup (Spring Boot & MySQL)

#### Prerequisites
- **Java 17+** installed (`java -version`)
- **Maven 3.8+** installed (`mvn -version`)
- **MySQL 8.x** running locally on port `3306`

#### Database Setup
Create the MySQL database (or let Hibernate create it automatically via the `createDatabaseIfNotExist=true` flag in `application.properties`):
```sql
CREATE DATABASE IF NOT EXISTS swasthya_sense;
```
If your MySQL root password is not `root`, update `backend/src/main/resources/application.properties`:
```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

#### Run the Backend
Open a terminal in the `/backend` folder:
```powershell
cd backend
mvn spring-boot:run
```
Upon launch:
- Hibernate auto-creates or updates the `patients` table schema (`spring.jpa.hibernate.ddl-auto=update`).
- The `DataSeeder` `CommandLineRunner` automatically seeds **10 synthetic, anonymized patient records** into the database.
- The high-risk patient alerts API is available at:
  👉 **`http://localhost:8080/api/patients/alerts`**

---

### 2. Frontend Setup (React, Vite & Tailwind)

#### Prerequisites
- **Node.js 18+** & **npm** installed (`node -v`, `npm -v`)

#### Run the Frontend
Open a separate terminal in the `/frontend` folder:
```powershell
cd frontend
npm install
npm run dev
```
Open your browser at:
👉 **`http://localhost:5173`**

---

## 🩺 Core Features

1. **Explainable Rule-Based Risk Flagging**
   - No black-box machine learning models.
   - Distinctly highlights `riskFlagReason` on each card (e.g., *"Missed 2 consecutive routine check-ins; Gestational hypertension monitor overdue"*).
   - Provides front-line health workers with complete transparency and clinical auditability.

2. **Front-Line Health Worker Workflow**
   - Active health worker session banner (e.g. Sister Anita Verma, ASHA Worker, PHC Rural Sector).
   - Quick counters for **High-Risk Maternal Alerts**, **Chronic Care Dropouts**, and **Escalated Cases**.
   - Search by patient pseudonym, health sub-center, or reason.
   - Filter by Pregnancy vs Chronic Care categories.

3. **Escalation Protocol ("Route to Clinician")**
   - Instant 1-click action to escalate flagged cases to the Primary Care Medical Officer.
   - Calls `POST /api/patients/{id}/route-to-clinician` and transitions status to `ROUTED_TO_CLINICIAN` with visual feedback.

4. **Privacy-Preserving Architecture**
   - Synthetic anonymized identifiers (`Patient #SS-8821`).
   - Masked telephone contacts (`+91 98XXX-XX821`).
   - Zero exposure of unneeded personal identification.

---

## 📡 REST API Reference

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/patients/alerts` | Fetches all high-risk patients where `requiresClinicalReview = true` |
| `GET` | `/api/patients` | Fetches directory of all monitored patients |
| `GET` | `/api/patients/{id}` | Fetches a single patient profile |
| `POST` | `/api/patients/{id}/route-to-clinician` | Updates patient status to `ROUTED_TO_CLINICIAN` |
| `GET` | `/api/patients/stats` | Aggregated surveillance statistics |

