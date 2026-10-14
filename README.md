# Anurag Alumni Connect - Backend (Spring Boot 3.3 + MySQL 8.0)

This is the independent Spring Boot backend for the Anurag University Alumni Connect platform.

## Architecture & Tech Stack
- **Framework**: Spring Boot 3.3.4 (Java 17/21)
- **Database**: MySQL 8.0+
- **Persistence**: Spring Data JPA / Hibernate
- **Security**: Spring Security 6 with stateless JWT authentication
- **Connection Pool**: HikariCP
- **Build Tool**: Apache Maven

## Directory Structure
```
backend/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/edu/anurag/alumni/
        │   ├── AnuragAlumniApplication.java
        │   ├── controller/
        │   │   ├── AuthController.java
        │   │   ├── OpportunityController.java
        │   │   ├── QuestionController.java
        │   │   ├── ReviewController.java
        │   │   ├── ProfileController.java
        │   │   └── AdminController.java
        │   ├── dto/
        │   │   ├── AuthDtos.java
        │   │   ├── OpportunityDto.java
        │   │   ├── QuestionDtos.java
        │   │   └── ReviewDtos.java
        │   ├── model/
        │   │   ├── User.java
        │   │   ├── StudentProfile.java
        │   │   ├── AlumniProfile.java
        │   │   ├── Opportunity.java
        │   │   ├── Question.java
        │   │   ├── Answer.java
        │   │   ├── ReviewRequest.java
        │   │   └── ReviewFeedback.java
        │   ├── repository/
        │   │   ├── UserRepository.java
        │   │   ├── OpportunityRepository.java
        │   │   ├── QuestionRepository.java
        │   │   └── ReviewRequestRepository.java
        │   ├── security/
        │   │   ├── JwtTokenProvider.java
        │   │   ├── JwtAuthenticationFilter.java
        │   │   └── SecurityConfig.java
        │   └── service/
        │       ├── AuthService.java
        │       ├── OpportunityService.java
        │       ├── QuestionService.java
        │       └── ReviewService.java
        └── resources/
            ├── application.yml
            ├── schema.sql
            └── data.sql
```

## Setup Instructions

### 1. MySQL Database Setup
Log into your MySQL instance:
```bash
mysql -u root -p
```
Run:
```sql
CREATE DATABASE anurag_alumni_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Load the schema and seed data:
```bash
mysql -u root -p anurag_alumni_db < src/main/resources/schema.sql
mysql -u root -p anurag_alumni_db < src/main/resources/data.sql
```

### 2. Configure Database Credentials
Edit `src/main/resources/application.yml` or export environment variables:
```bash
export MYSQL_USER=root
export MYSQL_PASSWORD=your_password
```

### 3. Build & Run Spring Boot
```bash
mvn clean package -DskipTests
mvn spring-boot:run
```

The REST API will start on: **http://localhost:8080/api/v1**
Health Check: **http://localhost:8080/api/v1/actuator/health**

### 4. Connect with Frontend (React)
The React frontend connects to `http://localhost:8080/api/v1`.
CORS is pre-configured for `http://localhost:3000` and `http://localhost:5173`.
