# RevHire - Job Portal Application

## 📝 Application Overview
RevHire is a console-based job portal application that connects job seekers with employers. Job seekers can create profiles, build resumes, search for jobs, and apply to positions. Employers can post job openings, manage applications, and shortlist/reject candidates. The application is designed with a modular architecture that can be extended to a microservices-based web application in future phases.

---

## 🎯 Core Functional Requirements

### 👤 For Job Seekers
- **Authentication**: Register and create an account with personal details; Login securely.
- **Resume Management**: Create and manage textual resumes with structured sections (objective, education, experience, skills, projects).
- **Job Search**: Advanced filtering by job role, location, experience years, company name, salary range, and job type.
- **Applications**: One-click apply using saved resumes; View application status (Applied/Shortlisted/Rejected/Withdrawn).
- **Withdrawal**: Withdraw applications with confirmation and optional reasons.
- **Profile Management**: Manage comprehensive details including education, work experience, skills, and certifications.
- **Notifications**: Receive in-app alerts for application status updates and job matches.

### 🏢 For Employers
- **Organization Management**: Register company and create account with details (name, industry, size, description, website, location).
- **Job Lifecycle**: Create, view, edit, close/reopen, or delete job postings.
- **Applicant Tracking**: View detailed applicant profiles, resumes, and application dates.
- **Decision Management**: Shortlist or reject applications with comments and status updates.
- **Statistics**: View statistics for each job posting to track recruitment progress.
- **Search**: Search and filter applicants by experience, skills, education, and application date.

---

## 🛠️ Standard Functional Scope

### Authentication & Account Management
- **Register**: Separate flows for job seekers and employers.
- **Login**: Secure access using email and password.
- **Security**: Change password (requires current) and reset password via security questions.
- **Profile Tracking**: Real-time profile completion tracking.

### Notification System
- In-app notifications for application status changes, new applications, and automated job matches.

---

## 🏗️ Architecture & Design

### Entity Relationship Diagram (ERD)
The application manages complex relationships between users, companies, jobs, and applications.

```mermaid
erDiagram
    USERS ||--o| JOB_SEEKERS : "specializes as"
    USERS ||--o| EMPLOYERS : "specializes as"
    EMPLOYERS ||--o{ COMPANIES : "manages"
    COMPANIES ||--o{ JOBS : "posts"
    
    JOB_SEEKERS ||--o{ RESUMES : "creates"
    JOB_SEEKERS ||--o{ APPLICATIONS : "submits"
    JOB_SEEKERS ||--o{ EDUCATION : "has"
    JOB_SEEKERS ||--o{ EXPERIENCE : "has"
    JOB_SEEKERS ||--o{ SKILLS : "possesses"
    JOB_SEEKERS ||--o{ CERTIFICATIONS : "achieves"
    JOB_SEEKERS ||--o{ PROJECTS : "completes"
    JOB_SEEKERS ||--o{ NOTIFICATIONS : "receives"

    JOBS ||--o{ APPLICATIONS : "receives"
    RESUMES ||--o{ APPLICATIONS : "used in"

    USERS {
        int user_id PK
        string email UK
        string password_hash
        string role
        boolean is_active
        timestamp created_at
        string security_question
        string security_answer
    }

    JOB_SEEKERS {
        int seeker_id PK
        int user_id FK
        string full_name
        string phone
        int total_experience
    }

    JOBS {
        int job_id PK
        int company_id FK
        string title
        string description
        string skills_required
        int min_experience
        double salary_min
        double salary_max
        string job_type
        date deadline
        string status
    }

    APPLICATIONS {
        int application_id PK
        int job_id FK
        int seeker_id FK
        int resume_id FK
        string status
        date applied_date
        string employer_comment
    }
```

### 🏗️ 3-Tier Layered Architecture
RevHire follows a strictly decoupled architecture to ensure maintainability and scalability:

![Application Architecture Diagram](assets/Application%20Architecture%20Diagram.png)

1.  **Presentation Layer (`com.revhire.ui`)**: Interactive console UI handling user I/O.
2.  **Service Layer (`com.revhire.service`)**: Business logic, validation, and orchestration.
3.  **Data Access Layer (`com.revhire.dao`)**: Direct interaction with Oracle DB using optimized JDBC queries.
4.  **Model Layer (`com.revhire.model`)**: POJO representations of business entities.

---

## 🛠️ Technology Stack

- **Core**: Java 17
- **Database**: Oracle Database
- **Persistence**: Optimized JDBC (No ORM for peak performance)
- **Build Tool**: Maven
- **Logging**: Log4j 2 (Structured auditing and error tracking)
- **Testing**: JUnit 5 & Mockito

---

## 📦 Getting Started

### Prerequisites
- **Java JDK 17** or higher
- **Maven 3.x**
- **Oracle Database** instance

### Database Setup
1. Configure your database credentials in `com.revhire.config.DBConnection`.
2. Run the provided SQL scripts (if available) to initialize the 12+ required tables.

### Installation
```bash
# Clone the repository
git clone <repository-url>

# Navigate to project directory
cd RevHire

# Build the project
mvn clean install
```

### Running the Application
```bash
# Run the main application
mvn exec:java -Dexec.mainClass="com.revhire.ui.MainMenu"
```

---

## 🧪 Testing & Quality Assurance
The project includes a suite of unit tests to ensure business logic reliability.

```bash
# Run all tests
mvn test
```
Tests utilize **Mockito** for mocking DAOs and services, ensuring isolation and fast execution.

---

## 📝 Logging
Systems logs and audits are maintained in:
- **Console Output**: Real-time operational feedback.
- **File Log**: Detailed logs at `logs/revhire.log`.
- **Daily Rotation**: Logs are automatically archived daily in the format `logs/revhire-YYYY-MM-DD.log`.

Configuration is managed via `src/main/resources/log4j2.xml`.

---

## ✅ Definition of Done
- [x] Working Console Application Demonstration.
- [x] Comprehensive ERD Diagram (Mermaid).
- [x] 3-Tier Application Architecture Diagram (Mermaid).
- [x] Unit Test Suite with JUnit 5 & Mockito.
- [x] JaCoCo Code Coverage (Configured for 91% minimum).
- [x] Log4j 2 Integration for structured auditing.

---

## 👨‍💻 Author
**Damarkeswar Reddy** - *Full Stack Developer & Architect*
