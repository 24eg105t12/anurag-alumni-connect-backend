package edu.anurag.alumni.service;

import edu.anurag.alumni.dto.AuthDtos.*;
import edu.anurag.alumni.model.AlumniProfile;
import edu.anurag.alumni.model.StudentProfile;
import edu.anurag.alumni.model.User;
import edu.anurag.alumni.repository.UserRepository;
import edu.anurag.alumni.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String name = user.getStudentProfile() != null ? user.getStudentProfile().getName() :
                      user.getAlumniProfile() != null ? user.getAlumniProfile().getName() : "Admin";

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole().name(), name);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserSummary.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role(user.getRole().name().toLowerCase())
                        .name(name)
                        .avatarUrl(null)
                        .build())
                .build();
    }

    @Transactional
    public AuthResponse registerStudent(RegisterStudentRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Account already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.STUDENT)
                .isVerified(true)
                .isActive(true)
                .build();

        StudentProfile profile = StudentProfile.builder()
                .user(user)
                .name(request.getName())
                .phone(request.getPhone())
                .branch(request.getBranch() != null ? request.getBranch() : "CSE")
                .batchYear(request.getBatchYear() != null ? request.getBatchYear() : 2026)
                .cgpa(request.getCgpa())
                .bio(request.getBio())
                .linkedinUrl(request.getLinkedinUrl())
                .githubUrl(request.getGithubUrl())
                .resumeUrl(request.getResumeUrl())
                .build();

        user.setStudentProfile(profile);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), "STUDENT", request.getName());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserSummary.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role("student")
                        .name(request.getName())
                        .build())
                .build();
    }

    @Transactional
    public AuthResponse registerAlumni(RegisterAlumniRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Account already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.ALUMNI)
                .isVerified(true)
                .isActive(true)
                .build();

        AlumniProfile profile = AlumniProfile.builder()
                .user(user)
                .name(request.getName())
                .phone(request.getPhone())
                .branch(request.getBranch() != null ? request.getBranch() : "CSE")
                .batchYear(request.getBatchYear() != null ? request.getBatchYear() : 2022)
                .currentCompany(request.getCurrentCompany())
                .currentRole(request.getCurrentRole())
                .experienceYears(request.getExperienceYears())
                .bio(request.getBio())
                .linkedinUrl(request.getLinkedinUrl())
                .githubUrl(request.getGithubUrl())
                .isMentor(request.getIsMentor() != null ? request.getIsMentor() : true)
                .build();

        user.setAlumniProfile(profile);
        userRepository.save(user);

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), "ALUMNI", request.getName());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(UserSummary.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .role("alumni")
                        .name(request.getName())
                        .build())
                .build();
    }
}
