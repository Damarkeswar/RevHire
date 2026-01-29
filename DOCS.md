# RevHire Application Documentation

## 1. Entity Relationship Diagram (ERD)

The following tables comprise the RevHire database schema:

### Users & Profiles
- **USERS**: Core authentication table.
  - `user_id` (PK), `email`, `password`, `role`, `security_question`, `security_answer`
- **JOB_SEEKERS**: Extended profile for candidates.
  - `job_seeker_id` (PK), `user_id` (FK), `full_name`, `phone`, `total_experience`
- **EMPLOYERS**: Connector for employer users.
  - `employer_id` (PK), `user_id` (FK)
- **COMPANIES**: Business details managed by employers.
  - `company_id` (PK), `employer_id` (FK), `company_name`, `industry`, `company_size`, etc.

### Job Management
- **JOBS**: Job postings created by companies.
  - `job_id` (PK), `company_id` (FK), `job_title`, `job_description`, `skills_required`, `education_required`, `salary_range`, `status`
- **APPLICATIONS**: Links seekers to jobs.
  - `application_id` (PK), `job_id` (FK), `job_seeker_id` (FK), `resume_id` (FK), `status`, `applied_date`
- **RESUMES**: Documents uploaded by seekers.
  - `resume_id` (PK), `job_seeker_id` (FK), `file_path`, `upload_date`

### Seeker Details
- **EDUCATION**: `education_id` (PK), `job_seeker_id` (FK), `degree`, `institution`, etc.
- **SKILLS**: `skill_id` (PK), `job_seeker_id` (FK), `skill_name`, `proficiency_level`
- **EXPERIENCE**: `experience_id` (PK), `job_seeker_id` (FK), `company_name`, `role`, etc.

### Communication
- **NOTIFICATIONS**: In-app alerts for job seekers.
- **EMPLOYER_NOTIFICATIONS**: In-app alerts for recruiters.

---

## 2. Application Architecture

The application follows a **3-Tier Architecture** pattern:

### Tier 1: Presentation (UI Layer)
- **Package**: `com.revhire.ui`
- **Responsibilities**: 
  - Handles all `Console` input/output.
  - Manages menu navigation and user workflow.
  - Displays formatted data and captures user choices.
- **Key Classes**: `MainMenu`, `JobSeekerDashboard`, `EmployerDashboard`, `JobSearchMenu`.

### Tier 2: Business Logic (Service Layer)
- **Package**: `com.revhire.service`
- **Responsibilities**:
  - Implements business rules (e.g., job matching logic, profile completion calculation).
  - Orchestrates calls between different DAOs.
  - Handles logging via **Log4J2**.
- **Key Classes**: `JobServiceImpl`, `AuthServiceImpl`, `ApplicationServiceImpl`.

### Tier 3: Data Access (DAO Layer)
- **Package**: `com.revhire.dao`
- **Responsibilities**:
  - Encapsulates all SQL logic.
  - Uses **JDBC PreparedStatements** for secure DB interaction.
  - Maps `ResultSet` objects to Java **Models** (`com.revhire.model`).
- **Key Classes**: `JobDaoImpl`, `UserDaoImpl`, `ApplicationDaoImpl`.

---

## 3. Technologies Used
- **Language**: Java 8+
- **Database**: Oracle DB (SQL Developer)
- **Build Tool**: Maven
- **Logging**: Log4J2
- **Testing**: JUnit 5
- **Version Control**: Git
