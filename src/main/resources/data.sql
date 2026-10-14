USE anurag_alumni_db;

-- Seed Users (Passwords are BCrypt hashes for 'Student@123', 'Alumni@123', 'Admin@123')
-- BCrypt hash for Admin@123: $2a$10$wT0l8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i
-- BCrypt hash for Alumni@123: $2a$10$1Y8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i
-- BCrypt hash for Student@123: $2a$10$2Z8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i

INSERT INTO users (id, email, password_hash, role, is_verified, is_active) VALUES
('user_admin', 'admin@anurag.edu.in', '$2a$10$wT0l8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i', 'ADMIN', TRUE, TRUE),
('user_karthik', 'karthik.r@anurag.edu.in', '$2a$10$1Y8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i', 'ALUMNI', TRUE, TRUE),
('user_priya', 'priya.s@anurag.edu.in', '$2a$10$1Y8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i', 'ALUMNI', TRUE, TRUE),
('user_riya', 'riya.m@anurag.edu.in', '$2a$10$2Z8k3K7UeX9V/gS1YgCevq8M8p1.VbQ3/XzG5uX/n8eQ5c8zJ1i', 'STUDENT', TRUE, TRUE)
ON DUPLICATE KEY UPDATE email=VALUES(email);

-- Seed Student Profile
INSERT INTO student_profiles (id, user_id, name, phone, branch, batch_year, cgpa, skills, bio, linkedin_url, github_url, resume_url) VALUES
('sp_riya', 'user_riya', 'Riya Mehta', '+91 98490 12345', 'CSE', 2026, 8.25, '["Python", "DSA", "React", "Machine Learning", "SQL", "Git"]', 'Pre-final year CSE student at Anurag University preparing for campus placements and technical interviews.', 'https://linkedin.com/in/riya-mehta-au', 'https://github.com/riya-mehta', 'riya_mehta_resume_2026.pdf')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Seed Alumni Profiles
INSERT INTO alumni_profiles (id, user_id, name, phone, branch, batch_year, current_company, current_role, experience_years, skills, bio, linkedin_url, github_url, is_mentor) VALUES
('ap_karthik', 'user_karthik', 'Karthik Reddy', '+91 94401 54321', 'CSE', 2022, 'Infosys', 'Software Development Engineer', 3, '["Java", "Spring Boot", "Python", "React", "SQL", "AWS", "DSA"]', 'Alumnus from 2022 B.Tech CSE batch. Working on enterprise microservices & cloud architectures at Infosys Hyderabad.', 'https://linkedin.com/in/karthik-reddy-au', 'https://github.com/karthik-au-dev', TRUE),
('ap_priya', 'user_priya', 'Priya Sharma', '+91 91234 98765', 'IT', 2021, 'Tata Consultancy Services (TCS)', 'Lead Data Analyst', 4, '["SQL", "Python", "Power BI", "Tableau", "Machine Learning", "Azure"]', '2021 IT graduate. Leading analytics pipelines and business intelligence at TCS Innovation Park.', 'https://linkedin.com/in/priya-sharma-tcs', 'https://github.com/priya-analytics', TRUE)
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Seed Opportunities
INSERT INTO opportunities (id, posted_by, title, company, type, location, is_remote, description, requirements, skills_required, salary_min, salary_max, eligible_branches, eligible_batches, min_cgpa, deadline, apply_link, is_referral_available, is_active) VALUES
('opp_1', 'user_karthik', 'Software Engineer Intern (SP & DSE)', 'Infosys', 'INTERNSHIP', 'Hyderabad (Gachibowli campus)', FALSE, 'Infosys is seeking dynamic engineering interns for the Special Programs engineering track. Work on cloud microservices and delivery pods.', 'B.Tech in CSE, AI & ML, IT, ECE. Solid knowledge of Java, Python, or C++.', '["Java", "Python", "SQL", "DSA", "Spring Boot"]', 25000, 30000, '["CSE", "AI & ML", "IT", "ECE/EEE"]', '[2026, 2027]', 7.00, '2026-06-30', 'https://career.infosys.com/jobdesc?jobId=AU_SE_2026', TRUE, TRUE),
('opp_2', 'user_priya', 'Associate Data & Analytics Engineer', 'Tata Consultancy Services (TCS)', 'FULL_TIME', 'Hyderabad / Bengaluru', FALSE, 'Entry level opportunity for graduating engineering students in TCS Digital & Analytics business unit.', 'B.Tech/BE in CSE, IT, or AI&ML. Familiarity with SQL and data pipelines.', '["SQL", "Python", "Power BI", "Tableau", "Azure"]', 700000, 850000, '["CSE", "AI & ML", "IT", "MCA"]', '[2025, 2026]', 7.50, '2026-05-15', 'https://ibegin.tcs.com/iBegin/jobs/entry-level-analytics', TRUE, TRUE)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- Seed Questions
INSERT INTO questions (id, author_id, title, body, category, tags, view_count, like_count, is_resolved, is_active) VALUES
('q_1', 'user_riya', 'How do I crack Infosys SP (Specialist Programmer) for CSE 2026 batch?', 'I have an Infosys Specialist Programmer hiring drive scheduled next month on campus. Could alumni please guide on the exact problem types asked in the 3-question coding assessment and interview rounds?', 'Placements', '["Placements", "DSA", "Infosys", "Interview"]', 142, 24, TRUE, TRUE)
ON DUPLICATE KEY UPDATE title=VALUES(title);

-- Seed Answers
INSERT INTO answers (id, question_id, author_id, body, upvotes, is_accepted) VALUES
('ans_1', 'q_1', 'user_karthik', 'Coding round has 3 questions in 3 hours. Focus heavily on 1 greedy, 1 dynamic programming, and 1 graph traversal. Cleared it from Anurag in 2022!', 19, TRUE)
ON DUPLICATE KEY UPDATE body=VALUES(body);
