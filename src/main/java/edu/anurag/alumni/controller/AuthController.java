package edu.anurag.alumni.controller;

import edu.anurag.alumni.dto.AuthDtos.*;
import edu.anurag.alumni.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register-student")
    public ResponseEntity<AuthResponse> registerStudent(@Valid @RequestBody RegisterStudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerStudent(request));
    }

    @PostMapping("/register-alumni")
    public ResponseEntity<AuthResponse> registerAlumni(@Valid @RequestBody RegisterAlumniRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerAlumni(request));
    }
}
