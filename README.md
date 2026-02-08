# RevHire - Job Portal Application

RevHire is a robust, console-based job portal application designed to bridge the gap between talented job seekers and leading employers. Built with a clean 3-tier architecture, it provides a seamless experience for managing job postings, profile building, and the recruitment process.

---

## 🚀 Key Features

### 👤 For Job Seekers
- **Authentication**: Secure registration and login with password recovery using security questions.
- **Profile Management**: Build a comprehensive professional profile including Education, Experience, Skills, and Certifications.
- **Resume Management**: Create and maintain detailed resumes.
- **Job Search**: Advanced filtering system to find jobs by title, location, experience, and salary.
- **One-Click Apply**: Easily apply to jobs using saved profiles and track application status in real-time.
- **Notifications**: Stay updated with in-app alerts for application status changes.

### 🏢 For Employers
- **Organization Management**: Register and manage company profiles and industry details.
- **Job Lifecycle**: Full control over job postings—create, edit, close, or reopen listings.
- **Applicant Tracking**: View detailed applicant profiles, shortlist candidates, or reject with feedback.
- **Applicant Search**: Filter through applicants to find the perfect fit for specific roles.

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

```mermaid
graph TD
    subgraph "Presentation Layer (UI)"
        MM[MainMenu]
        JSD[JobSeekerDashboard]
        ED[EmployerDashboard]
        Menus[Specific Menus: Job, Profile, Resume, etc.]
    end

    subgraph "Service Layer (Business Logic)"
        JS[JobService]
        USS[UserService]
        RS[ResumeService]
        AS[ApplicationService]
        NS[NotificationService]
    end

    subgraph "Data Access Layer (DAO)"
        JDAO[JobDAO]
        UDAO[UserDAO]
        RDAO[ResumeDAO]
        ADAO[ApplicationDAO]
        NDAO[NotificationDAO]
    end

    subgraph "Models & Config"
        Model[POJO Entities]
        DBConfig[DBConnection Singleton]
    end

    MM --> JSD
    MM --> ED
    JSD --> Menus
    ED --> Menus
    Menus --> JS
    Menus --> USS
    
    JS & USS & RS & AS & NS --> JDAO
    JS & USS & RS & AS & NS --> UDAO
    JS & USS & RS & AS & NS --> RDAO
    
    JDAO & UDAO & RDAO & ADAO & NDAO --> DBConfig
    DBConfig --> DB[(Oracle DB)]
```

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

Configuration is managed via `src/main/resources/log4j2.xml`.

---

## 👨‍💻 Author
**Lead Developer** - RevHire Team
