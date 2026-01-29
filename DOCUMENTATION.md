# RevHire - Job Portal Application

RevHire is a console-based job portal application that connects job seekers with employers.

## Core Features

### For Job Seekers
- **Authentication**: Register, Login, Forgot Password (Security Questions).
- **Profile Management**: Manage Education, Experience, Skills, and Certifications.
- **Resume Management**: Create and manage textual resumes.
- **Job Search**: Search jobs with multiple filters (role, location, experience, etc.).
- **Applications**: One-click apply using saved resume, view application status, and withdraw applications.
- **Notifications**: In-app notifications for status updates.

### For Employers
- **Company Management**: Register company and manage company profile.
- **Job Management**: Post new jobs, edit existing postings, close/reopen or delete jobs.
- **Applicant Management**: View applicants for each job, shortlist or reject applications with comments.
- **Search**: Search/Filter applicants.

---

## Entity Relationship Diagram (ERD)

```mermaid
erDiagram
    USERS ||--o| JOB_SEEKERS : "is a"
    USERS ||--o| EMPLOYERS : "is a"
    EMPLOYERS ||--o| COMPANIES : "manages"
    COMPANIES ||--o{ JOBS : "posts"
    JOB_SEEKERS ||--o{ RESUMES : "has"
    JOBS ||--o{ APPLICATIONS : "receives"
    JOB_SEEKERS ||--o{ APPLICATIONS : "submits"
    RESUMES ||--o{ APPLICATIONS : "used in"
    JOB_SEEKERS ||--o{ NOTIFICATIONS : "receives"
    
    USERS {
        int user_id PK
        string email
        string password_hash
        string role
        char is_active
        timestamp created_at
        string security_question
        string security_answer
    }
    
    COMPANIES {
        int company_id PK
        int employer_id FK
        string company_name
        string industry
        int company_size
        string description
        string website
        string location
    }
    
    JOBS {
        int job_id PK
        int company_id FK
        string job_title
        string job_description
        string skills_required
        int min_experience
        string location
        double salary_min
        double salary_max
        string job_type
        date deadline
        string status
    }
    
    APPLICATIONS {
        int application_id PK
        int job_id FK
        int job_seeker_id FK
        int resume_id FK
        string status
        date applied_date
        string employer_comment
    }
```

---

## Application Architecture

The application follows a standard **layered architecture**:

1.  **Presentation Layer (UI)**:
    -   Located in `com.revhire.ui`.
    -   Handles user input/output via console.
    -   Calls service layer methods.

2.  **Service Layer (Business Logic)**:
    -   Located in `com.revhire.service`.
    -   Contains interfaces and implementations.
    -   Handles business rules and coordinates between DAOs.

3.  **Data Access Layer (DAO)**:
    -   Located in `com.revhire.dao`.
    -   Contains interfaces and JDBC-based implementations.
    -   Handles all database interactions.

4.  **Model Layer**:
    -   Located in `com.revhire.model`.
    -   Contains Plain Old Java Objects (POJOs) representing database entities.

5.  **Configuration**:
    -   `com.revhire.config.DBConnection` handles database connectivity.
