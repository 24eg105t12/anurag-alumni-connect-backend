-- ==============================================================
-- Anurag Alumni Connect (AAC) - MySQL Database Schema
-- Compatible with MySQL 8.0+
-- Database: anurag_alumni_db
-- ==============================================================

CREATE DATABASE IF NOT EXISTS anurag_alumni_db 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE anurag_alumni_db;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'ALUMNI', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_role (role),
    INDEX idx_user_email (email)
) ENGINE=InnoDB;

-- 2. Student Profiles Table
CREATE TABLE IF NOT EXISTS student_profiles (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    branch VARCHAR(50) NOT NULL,
    batch_year INT NOT NULL,
    cgpa DECIMAL(4,2),
    skills JSON,
    bio TEXT,
    linkedin_url VARCHAR(255),
    github_url VARCHAR(255),
    avatar_url VARCHAR(500),
    resume_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_student_branch (branch),
    INDEX idx_student_batch (batch_year)
) ENGINE=InnoDB;

-- 3. Alumni Profiles Table
CREATE TABLE IF NOT EXISTS alumni_profiles (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(20),
    branch VARCHAR(50) NOT NULL,
    batch_year INT NOT NULL,
    current_company VARCHAR(150) NOT NULL,
    current_role VARCHAR(150) NOT NULL,
    experience_years INT DEFAULT 0,
    skills JSON,
    bio TEXT,
    linkedin_url VARCHAR(255),
    github_url VARCHAR(255),
    avatar_url VARCHAR(500),
    is_mentor BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_alumni_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_alumni_company (current_company),
    INDEX idx_alumni_branch (branch)
) ENGINE=InnoDB;

-- 4. Questions Table
CREATE TABLE IF NOT EXISTS questions (
    id VARCHAR(36) PRIMARY KEY,
    author_id VARCHAR(36) NOT NULL,
    title VARCHAR(300) NOT NULL,
    body MEDIUMTEXT NOT NULL,
    category VARCHAR(50) DEFAULT 'Placements',
    tags JSON,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    is_resolved BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_question_author FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_q_created (created_at DESC),
    INDEX idx_q_category (category)
) ENGINE=InnoDB;

-- 5. Answers Table
CREATE TABLE IF NOT EXISTS answers (
    id VARCHAR(36) PRIMARY KEY,
    question_id VARCHAR(36) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    body MEDIUMTEXT NOT NULL,
    upvotes INT DEFAULT 0,
    is_accepted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    CONSTRAINT fk_answer_author FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_ans_question (question_id)
) ENGINE=InnoDB;

-- 6. Question Likes Table (Prevents double-liking)
CREATE TABLE IF NOT EXISTS question_likes (
    id VARCHAR(36) PRIMARY KEY,
    question_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_q_like (question_id, user_id),
    CONSTRAINT fk_qlike_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    CONSTRAINT fk_qlike_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 7. Opportunities Table (Jobs & Internships)
CREATE TABLE IF NOT EXISTS opportunities (
    id VARCHAR(36) PRIMARY KEY,
    posted_by VARCHAR(36) NOT NULL,
    title VARCHAR(200) NOT NULL,
    company VARCHAR(150) NOT NULL,
    type ENUM('INTERNSHIP', 'FULL_TIME', 'CONTRACT', 'FREELANCE') NOT NULL,
    location VARCHAR(150),
    is_remote BOOLEAN DEFAULT FALSE,
    description MEDIUMTEXT NOT NULL,
    requirements TEXT,
    skills_required JSON,
    salary_min INT,
    salary_max INT,
    eligible_branches JSON,
    eligible_batches JSON,
    min_cgpa DECIMAL(4,2),
    deadline DATE,
    apply_link VARCHAR(500) NOT NULL,
    is_referral_available BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_opp_poster FOREIGN KEY (posted_by) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_opp_active (is_active),
    INDEX idx_opp_deadline (deadline)
) ENGINE=InnoDB;

-- 8. Review Requests Table (Resume & Project Mentorship)
CREATE TABLE IF NOT EXISTS review_requests (
    id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    reviewer_id VARCHAR(36) NULL,
    type ENUM('RESUME', 'PROJECT', 'BOTH') NOT NULL,
    status ENUM('PENDING', 'IN_REVIEW', 'COMPLETED') DEFAULT 'PENDING',
    message TEXT,
    resume_url VARCHAR(500),
    project_title VARCHAR(200),
    project_url VARCHAR(500),
    project_tech JSON,
    project_description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    claimed_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    CONSTRAINT fk_rev_student FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_rev_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_rev_status (status)
) ENGINE=InnoDB;

-- 9. Review Feedback Table
CREATE TABLE IF NOT EXISTS review_feedback (
    id VARCHAR(36) PRIMARY KEY,
    request_id VARCHAR(36) NOT NULL UNIQUE,
    reviewer_id VARCHAR(36) NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    feedback MEDIUMTEXT NOT NULL,
    strengths JSON,
    improvements JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fb_request FOREIGN KEY (request_id) REFERENCES review_requests(id) ON DELETE CASCADE,
    CONSTRAINT fk_fb_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 10. Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'system',
    link VARCHAR(255),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_notif_user_read (user_id, is_read)
) ENGINE=InnoDB;
